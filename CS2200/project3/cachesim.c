#include "cachesim.h"
#include <stdlib.h>
#include <stdio.h>

#define TRUE 1
#define FALSE 0

/**
 * The stuct that you may use to store the metadata for each block in the L1 and L2 caches
 */
typedef struct block_t {
    uint64_t tag; // The tag stored in that block
    uint8_t valid; // Valid bit
    uint8_t dirty; // Dirty bit
    uint64_t lru;

} block;


/**
 * A struct for storing the configuration of both the L1 and L2 caches as passed in the
 * cache_init function. All values represent number of bits. You may add any parameters
 * here, however I strongly suggest not removing anything from the config struct
 */
typedef struct config_t {
    uint64_t C1; /* Size of cache L1 */
    uint64_t C2; /* Size of cache L2 */
    uint64_t S; /* Set associativity of L2 */
    uint64_t B; /* Block size of both caches */
} config;

/****** Do not modify the below function headers ******/
static uint64_t get_tag(uint64_t address, uint64_t C, uint64_t B, uint64_t S);

static uint64_t get_index(uint64_t address, uint64_t C, uint64_t B, uint64_t S);

static uint64_t convert_tag(uint64_t tag, uint64_t index, uint64_t C1, uint64_t C2, uint64_t B, uint64_t S);

static uint64_t convert_index(uint64_t tag, uint64_t index, uint64_t C1, uint64_t C2, uint64_t B, uint64_t S);

static uint64_t convert_tag_l1(uint64_t l2_tag, uint64_t l2_index, uint64_t C1, uint64_t C2, uint64_t B, uint64_t S);

static uint64_t convert_index_l1(uint64_t l2_tag, uint64_t l2_index, uint64_t C1, uint64_t C2, uint64_t B, uint64_t S);

/****** You may add Globals and other function headers that you may need below this line ******/
void move_from_l2_to_l1(block *b, uint64_t l2_index, uint64_t t);

static uint64_t create_mask(int size);

typedef struct {
    block *blocks;
} cache;

config cache_config;
cache *l1;
cache *l2;
int clock = 0;

/**
 * Subroutine for initializing your cache with the passed in arguments.
 * You may initialize any globals you might need in this subroutine
 *
 * @param C1 The total size of your L1 cache is 2^C1 bytes
 * @param C2 The tatal size of your L2 cache is 2^C2 bytes
 * @param S The total number of blocks in a line/set of your L2 cache are 2^S
 * @param B The size of your blocks is 2^B bytes
 */
void cache_init(uint64_t C1, uint64_t C2, uint64_t S, uint64_t B) {
    /* 
        Initialize the caches here. I strongly suggest using arrays for representing
        meta data stored in the caches. The block_t struct given above maybe useful
    */
    cache_config.B = B;
    cache_config.C1 = C1;
    cache_config.C2 = C2;
    cache_config.S = S;

    uint64_t l1_num_blocks = (uint64_t) 1 << (C1 - B);
    uint64_t l2_num_blocks = (uint64_t) 1 << (C2 - B - S);

    //init L1
    l1 = (cache *) malloc(sizeof(cache));
    l1->blocks = (block *) malloc(l1_num_blocks * sizeof(block));
    for (int i = 0; i < l1_num_blocks; i++) {
        l1->blocks[i].tag = 0;
        l1->blocks[i].dirty = 0;
        l1->blocks[i].valid = 0;
        l1->blocks[i].lru = 0;
    }
    //init L2
    l2 = (cache *) malloc(sizeof(cache) * cache_config.S);
    for (int i = 0; i < S; i++) {
        l2[i].blocks = (block *) malloc(l2_num_blocks * sizeof(block));
        for (int j = 0; j < l2_num_blocks; j++) {
            l2[i].blocks[j].tag = 0;
            l2[i].blocks[j].dirty = 0;
            l2[i].blocks[j].valid = 0;
            l2[i].blocks[j].lru = 0;

        }
    }
}

/**
 * Subroutine that simulates one cache event at a time.
 * @param rw The type of access, READ or WRITE
 * @param address The address that is being accessed
 * @param stats The struct that you are supposed to store the stats in
 */
void cache_access(char rw, uint64_t address, struct cache_stats_t *stats) {
    /*
        Suggested approach:
            -> Find the L1 tag and index of the address that is being passed in to the function
            -> Check if there is a hit in the L1 cache
            -> If L1 misses, check the L2 cache for a hit (Hint: If L2 hits, update L1 with new values)
            -> If L2 misses, need to get values from memory, and update L2 and L1 caches

            * We will leave it upto you to decide what must be updated and when
     */
    stats->accesses += 1;
    stats->reads += (int) (rw == 'r');
    stats->writes += (int) (rw == 'w');

    clock = clock + 1;

    uint64_t tag = get_tag(address, cache_config.C1, cache_config.B, 0);
    uint64_t index = get_index(address, cache_config.C1, cache_config.B, 0);
    uint64_t l2_index = convert_index(tag, index, cache_config.C1, cache_config.C2, cache_config.B, cache_config.S);
    uint64_t l2_tag = convert_tag(tag, index, cache_config.C1, cache_config.C2, cache_config.B, cache_config.S);

    int found = 0;
    uint64_t oldest_block = 0;

    if (l1->blocks[index].tag == tag && l1->blocks[index].valid == 1) {
        // found in l1
        found = 1;
        block *b = &l1->blocks[index];
        if (rw == 'w') {
            b->dirty = 1;
        }
        b->lru = clock;
    } else {
        stats->l1_read_misses += (int) (rw == 'r');
        stats->l1_write_misses += (int) (rw == 'w');
    }

    for (int i = 0; i < cache_config.S; i++) {
        if (l2[i].blocks[l2_index].tag == l2_tag && l2[i].blocks[l2_index].valid == 1) {
            //found in l2
            block *b = &l2[i].blocks[l2_index];
            if (found == 0) {
                move_from_l2_to_l1(b, l2_index, l2_tag);
                if (rw == 'w') {
                    b->dirty = 1;
                }
            }
            found = 1;
            b->lru = clock;
        }
    }

    if (found == 0) {        //fetch from memory
        for (int i = 0; i < cache_config.S; i++) {
            //identify oldest block in case of replacement
            if (l2[i].blocks[l2_index].lru < l2[oldest_block].blocks[l2_index].lru) {
                oldest_block = i;
            }
        }
        stats->l2_read_misses += (int) (rw == 'r');
        stats->l2_write_misses += (int) (rw == 'w');

        block *b = &l2[oldest_block].blocks[l2_index];
        if (b->dirty == 1 && b->valid == 1) {
            stats->write_backs += 1;
        }
        b->valid = 1;
        b->tag = l2_tag;
        b->lru = clock;
        b->dirty = (int) (rw == 'w');
        move_from_l2_to_l1(b, l2_index, l2_tag);
    }
}

void move_from_l2_to_l1(block *b, uint64_t l2_index, uint64_t t) {
    uint64_t index = convert_index_l1(t, l2_index, cache_config.C1, cache_config.C2, cache_config.B,
                                      cache_config.S);
    uint64_t tag = convert_tag_l1(t, l2_index, cache_config.C1, cache_config.C2, cache_config.B, cache_config.S);

    if (l1->blocks[index].valid == 1) { //write back to l2
        uint64_t l2_ind = convert_index(l1->blocks[index].tag, index, cache_config.C1, cache_config.C2, cache_config.B,
                                        cache_config.S);
        uint64_t l2_t = convert_tag(l1->blocks[index].tag, index, cache_config.C1, cache_config.C2, cache_config.B,
                                    cache_config.S);
        for (int i = 0; i < cache_config.S; i++) {
            if (l2[i].blocks[l2_ind].tag == l2_t && l2[i].blocks[l2_ind].valid == 1) {
                l2[i].blocks[l2_ind].dirty = l1->blocks[index].dirty;
                l2[i].blocks[l2_ind].lru = l1->blocks[index].lru;
            }
        }
    }

    l1->blocks[index].lru = b->lru;
    l1->blocks[index].tag = tag;
    l1->blocks[index].valid = b->valid;
    l1->blocks[index].dirty = b->dirty;
}

/**
 * Subroutine for freeing up memory, and performing any final calculations before the statistics
 * are outputed by the driver
 */
void cache_cleanup(struct cache_stats_t *stats) {
    /*
        Make sure to free up all the memory you malloc'ed here. To check if you have freed up the
        the memory, run valgrind. For more information, google how to use valgrind.
    */
    stats->read_misses = stats->l1_read_misses + stats->l2_read_misses;
    stats->write_misses = stats->l1_write_misses + stats->l2_write_misses;
    stats->misses = stats->read_misses + stats->write_misses;
    stats->miss_rate = ((double) (stats->misses)) / ((double) (stats->accesses));
    stats->l1_miss_rate = ((double) (stats->l1_read_misses + stats->l1_write_misses)) / ((double) (stats->accesses));
    stats->l2_miss_rate = ((double) (stats->l2_read_misses + stats->l2_write_misses)) / ((double) (stats->accesses));
    free(l1->blocks);
    free(l1);

    for (int i = 0; i < cache_config.S; i++) {
        free(l2[i].blocks);
    }
    free(l2);
}

static uint64_t create_mask(int size) {
    int mask = 1;
    for (int i = 1; i < size; i++) {
        mask |= mask << 1;
    }
    return (uint64_t) mask;
}

/**
 * Subroutine to compute the Tag of a given address based on the parameters passed in
 *
 * @param address The address whose tag is to be computed
 * @param C The size of the cache in bits (i.e. Size of cache is 2^C)
 * @param B The size of the cache block in bits (i.e. Size of block is 2^B)
 * @param S The set associativity of the cache in bits (i.e. Set-Associativity is 2^S)
 * 
 * @return The computed tag
 */
static uint64_t get_tag(uint64_t address, uint64_t C, uint64_t B, uint64_t S) {
    return address >> C;
}

/**
 * Subroutine to compute the Index of a given address based on the parameters passed in
 *
 * @param address The address whose tag is to be computed
 * @param C The size of the cache in bits (i.e. Size of cache is 2^C)
 * @param B The size of the cache block in bits (i.e. Size of block is 2^B)
 * @param S The set associativity of the cache in bits (i.e. Set-Associativity is 2^S)
 *
 * @return The computed index
 */
static uint64_t get_index(uint64_t address, uint64_t C, uint64_t B, uint64_t S) {
    return (address >> (B - S)) & create_mask(C - B - S);
}


/**** DO NOT MODIFY CODE BELOW THIS LINE UNLESS YOU ARE ABSOLUTELY SURE OF WHAT YOU ARE DOING ****/

/*
    Note:   The below functions will be useful in converting the L1 tag and index into corresponding L2
            tag and index. These should be used when you are evicitng a block from the L1 cache, and
            you need to update the block in L2 cache that corresponds to the evicted block.

            The newly added functions will be useful for converting L2 indecies ang tags into the corresponding
            L1 index and tags. Make sure to understand how they are working.
*/

/**
 * This function converts the tag stored in an L1 block and the index of that L1 block into corresponding
 * tag of the L2 block
 *
 * @param tag The tag that needs to be converted (i.e. L1 tag)
 * @param index The index of the L1 cache (i.e. The index from which the tag was found)
 * @param C1 The size of the L1 cache in bits
 * @param C2 The size of the l2 cache in bits
 * @param B The size of the block in bits
 * @param S The set associativity of the L2 cache
 */
static uint64_t convert_tag(uint64_t tag, uint64_t index, uint64_t C1, uint64_t C2, uint64_t B, uint64_t S) {
    uint64_t reconstructed_address = (tag << (C1 - B)) | index;
    return reconstructed_address >> (C2 - B - S);
}

/**
 * This function converts the tag stored in an L1 block and the index of that L1 block into corresponding
 * index of the L2 block
 *
 * @param tag The tag stored in the L1 index
 * @param index The index of the L1 cache (i.e. The index from which the tag was found)
 * @param C1 The size of the L1 cache in bits
 * @param C2 The size of the l2 cache in bits
 * @param B The size of the block in bits
 * @param S The set associativity of the L2 cache
 */
static uint64_t convert_index(uint64_t tag, uint64_t index, uint64_t C1, uint64_t C2, uint64_t B, uint64_t S) {
    // Reconstructed address without the block offset bits
    uint64_t reconstructed_address = (tag << (C1 - B)) | index;
    // Create index mask for L2 without including the block offset bits
    return reconstructed_address & ((1 << (C2 - S - B)) - 1);
}

/**
 * This function converts the tag stored in an L2 block and the index of that L2 block into corresponding
 * tag of the L1 cache
 *
 * @param l2_tag The L2 tag
 * @param l2_index The index of the L2 block
 * @param C1 The size of the L1 cache in bits
 * @param C2 The size of the l2 cache in bits
 * @param B The size of the block in bits
 * @param S The set associativity of the L2 cache
 * @return The L1 tag linked to the L2 index and tag
 */
static uint64_t convert_tag_l1(uint64_t l2_tag, uint64_t l2_index, uint64_t C1, uint64_t C2, uint64_t B, uint64_t S) {
    uint64_t reconstructed_address = (l2_tag << (C2 - B - S)) | l2_index;
    return reconstructed_address >> (C1 - B);
}

/**
 * This function converts the tag stored in an L2 block and the index of that L2 block into corresponding
 * index of the L1 block
 *
 * @param l2_tag The L2 tag
 * @param l2_index The index of the L2 block
 * @param C1 The size of the L1 cache in bits
 * @param C2 The size of the l2 cache in bits
 * @param B The size of the block in bits
 * @param S The set associativity of the L2 cache
 * @return The L1 index of the L2 block
 */
static uint64_t convert_index_l1(uint64_t l2_tag, uint64_t l2_index, uint64_t C1, uint64_t C2, uint64_t B, uint64_t S) {
    uint64_t reconstructed_address = (l2_tag << (C2 - B - S)) | l2_index;
    return reconstructed_address & ((1 << (C1 - B)) - 1);
}
