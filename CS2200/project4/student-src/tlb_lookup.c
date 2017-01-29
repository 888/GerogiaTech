#include "tlb.h"
#include "pagetable.h"
#include <assert.h>
#include <global.h>

/**
 * This function simulates a the TLB lookup, and uses the second chance algorithm
 * to evict an entry
 *
 * @param vpn The virtual page number that has to be translated
 * @param offset The page offset of the virtual address
 * @param rw Specifies if the access is a read or a write
 * @param stats The struct for statistics
 */
uint64_t tlb_lookup(uint64_t vpn, uint64_t offset, char rw, stats_t *stats) {

    // (1) Look for the pfn in the TLB.
    // If you find it here
    // (2) update the frequency count of the page table entry using the
    //     current_pagetable global.
    // (3) Mark the TLB entry as used - for clock sweep
    // (4) Make sure to mark the entry dirty in the TLB if it is a write access
    // (5) return the PFN you just found in the TLB


    /********* TODO ************/
    stats->accesses++;
    stats->reads += (int) (rw == 'r');
    stats->writes += (int) (rw == 'w');

    uint64_t tlb_num_e = 1 << tlb_size;
    for (int i = 0; i < tlb_num_e; i++) {
        if (tlb[i].vpn == vpn && tlb[i].valid) {
            tlb[i].used = 1;
            if (rw == 'w') {
                tlb[i].dirty = 1;
            }
            current_pagetable[vpn].frequency++;
            return (tlb[i].pfn << page_size) | offset;
        }
    }
    stats->translation_faults++;
    // The below function is called if it is a TLB miss
    /* DO NOT MODIFY */
    uint64_t pfn = page_lookup(vpn, offset, rw, stats);
    /*****************/

    // (1) Update the relevant stats
    // (2) Update the TLB with this new mapping
    //      (a) Find an invalid block in the TLB - use it
    //      If you cannot find an invalid block
    //      (i) Run the clock sweep algorithm to find a victim
    //      (ii) Update the current_pagetable at that VPN to dirty if
    //           the evicted TLB entry is dirty
    //      (b) Put the new mapping into the TLB - mark it used

    /**** THESE WILL HELP YOU MAKE SURE THAT YOUR CODE IS CORRECT *****/
    assert(current_pagetable[vpn].valid);
    assert(pfn == current_pagetable[vpn].pfn);
    /********* TODO ************/
    uint64_t victim = -1;
    for (int i = 0; (i < tlb_num_e) && (victim == -1); i++) {
        if (!tlb[i].valid) {
            victim = i;
        }
    }

    if (victim == -1) {

        for (int i = 0; (i < tlb_num_e) && (victim == -1); i++) {
            if (tlb[i].used == 0) {
                victim = i;
            } else {
                tlb[i].used = 0;
            }
        }
    }
    if (victim == -1) {
        victim = 0;
    }

    tlb[victim].used = 1;
    tlb[victim].dirty = (int) (rw == 'w');
    tlb[victim].pfn = pfn;
    tlb[victim].vpn = vpn;
    tlb[victim].valid = 1;
    return (pfn << page_size) | offset;

}

