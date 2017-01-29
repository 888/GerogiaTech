#include "stats.h"

/** 
 * Use this function to compute the final statistics of the simulation
 *
 * @param stats The struct used for book keeping.
 *
 * Refer the "stats.h" file in the simulator-src
 */
void compute_stats(stats_t *stats) {
    /********* TODO ************/

    // Use the passed in struct to make sure all the stats are computed

    // Think of how many times the tlb, memory and disk are accessed when you have
    // 1) A translation fault
    // 2) A page fault - Disk is read here at least once
    // 3) A dirty page is evicted - Disk is written to here
    // 4) For every access regardless of translation fault and page fault
    stats->AAT = stats->TLB_READ_TIME +
                    stats->DISK_READ_TIME * stats->page_faults / stats->accesses +
                    stats->DISK_WRITE_TIME * stats->writes_to_disk / stats->accesses;


}

/**

typedef struct stats_t
{
	// Reads, writes and accesses
	uint64_t writes;
	uint64_t reads;
	uint64_t accesses;

	// Accesses that result in a page fault
	uint64_t page_faults;

	// Accesses that result in a TLB miss
	uint64_t translation_faults;

	uint64_t writes_to_disk;
	uint64_t reads_from_disk;
	// Average Access Time
	double AAT;

	// Constants
	uint64_t TLB_READ_TIME;
	uint64_t DISK_READ_TIME;
	uint64_t DISK_WRITE_TIME;
	uint64_t MEMORY_READ_TIME;
} stats_t;
 */