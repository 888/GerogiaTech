/*
 * student.c
 * Multithreaded OS Simulation for CS 2200, Project 5
 * Fall 2016
 *
 * This file contains the CPU scheduler for the simulation.
 * Name:
 * GTID:
 */

#include <assert.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#include "os-sim.h"


/*
 * current[] is an array of pointers to the currently running processes.
 * There is one array element corresponding to each CPU in the simulation.
 *
 * current[] should be updated by schedule() each time a process is scheduled
 * on a CPU.  Since the current[] array is accessed by multiple threads, you
 * will need to use a mutex to protect it.  current_mutex has been provided
 * for your use.
 */
static pcb_t **current;
static pthread_mutex_t current_mutex;

static pcb_t *ready_q;
static pthread_mutex_t ready_q_mutex;
static pthread_cond_t ready_q_cond;
static int timeslice = -1;
static int p = 0;
int cpu_count;

/*
 * schedule() is your CPU scheduler.  It should perform the following tasks:
 *
 *   1. Select and remove a runnable process from your ready queue which
 *	you will have to implement with a linked list or something of the sort.
 *
 *   2. Set the process state to RUNNING
 *
 *   3. Call context_switch(), to tell the simulator which process to execute
 *      next on the CPU.  If no process is runnable, call context_switch()
 *      with a pointer to NULL to select the idle process.
 *	The current array (see above) is how you access the currently running
 *	process indexed by the cpu id. See above for full description.
 *	context_switch() is prototyped in os-sim.h. Look there for more information
 *	about it and its parameters.
 */
static void schedule(unsigned int cpu_id) {
    if (ready_q == NULL) {
        context_switch(cpu_id, NULL, timeslice);
    } else {
        pthread_mutex_lock(&ready_q_mutex);
        pcb_t *t = ready_q;
        ready_q = ready_q->next;
        pthread_mutex_unlock(&ready_q_mutex);

        t->state = PROCESS_RUNNING;

        pthread_mutex_lock(&current_mutex);
        current[cpu_id] = t;
        pthread_mutex_unlock(&current_mutex);

        context_switch(cpu_id, t, timeslice);
    }
}


/*
 * idle()
static pthread_mutex_t current_mutex is your idle process.  It is called by the simulator when the idle
 * process is scheduled.
 *
 * This function should block until a process is added to your ready queue.
 * It should then call schedule() to select the process to run on the CPU.
 */
extern void idle(unsigned int cpu_id) {
    /* FIX ME */
    pthread_mutex_lock(&ready_q_mutex);
    while (ready_q == NULL) {
        pthread_cond_wait(&ready_q_cond, &ready_q_mutex);

    }
    pthread_mutex_unlock(&ready_q_mutex);
    schedule(cpu_id);
    /*
     * REMOVE THE LINE BELOW AFTER IMPLEMENTING IDLE()
     *
     * idle() must block when the ready queue is empty, or else the CPU threads
     * will spin in a loop.  Until a ready queue is implemented, we'll put the
     * thread to sleep to keep it from consuming 100% of the CPU time.  Once
     * you implement a proper idle() function using a condition variable,
     * remove the call to mt_safe_usleep() below.
     */
//    mt_safe_usleep(1000000);
}


/*
 * preempt() is the handler called by the simulator when a process is
 * preempted due to its timeslice expiring.
 *
 * This function should place the currently running process back in the
 * ready queue, and call schedule() to select a new runnable process.
 */
extern void preempt(unsigned int cpu_id) {

    pthread_mutex_lock(&current_mutex);
    current[cpu_id]->state = PROCESS_WAITING;
    pthread_mutex_unlock(&current_mutex);

    pthread_mutex_lock(&ready_q_mutex);
    if (ready_q == NULL) {
        ready_q = current[cpu_id];
    } else {
        pcb_t *last = ready_q;
        while (last->next != NULL) {
            last = last->next;
        }
        current[cpu_id]->next = NULL;
        last->next = current[cpu_id];
    }
    pthread_cond_signal(&ready_q_cond);
    pthread_mutex_unlock(&ready_q_mutex);

    schedule(cpu_id);

}


/*
 * yield() is the handler called by the simulator when a process yields the
 * CPU to perform an I/O request.
 *
 * It should mark the process as WAITING, then call schedule() to select
 * a new process for the CPU.
 */
extern void yield(unsigned int cpu_id) {
    pthread_mutex_lock(&current_mutex);
    current[cpu_id]->state = PROCESS_WAITING;
    pthread_mutex_unlock(&current_mutex);
    schedule(cpu_id);
}


/*
 * terminate() is the handler called by the simulator when a process completes.
 * It should mark the process as terminated, then call schedule() to select
 * a new process for the CPU.
 */
extern void terminate(unsigned int cpu_id) {
    pthread_mutex_lock(&current_mutex);
    current[cpu_id]->state = PROCESS_TERMINATED;
    pthread_mutex_unlock(&current_mutex);
    schedule(cpu_id);

}


/*
 * wake_up() is the handler called by the simulator when a process's I/O
 * request completes.  It should perform the following tasks:
 *
 *   1. Mark the process as READY, and insert it into the ready queue.
 *
 *   2. If the scheduling algorithm is static priority, wake_up() may need
 *      to preempt the CPU with the lowest priority process to allow it to
 *      execute the process which just woke up.  However, if any CPU is
 *      currently running idle, or all of the CPUs are running processes
 *      with a higher priority than the one which just woke up, wake_up()
 *      should not preempt any CPUs.
 *	To preempt a process, use force_preempt(). Look in os-sim.h for
 * 	its prototype and the parameters it takes in.
 */
extern void wake_up(pcb_t *process) {
    process->state = PROCESS_READY;
    process->next = NULL;
    if (p) {
        pthread_mutex_lock(&current_mutex);
        int id = -1;
        int lowestP = process->static_priority;
        for (int i = 0; i < cpu_count; i++) {
            if (current[i] == NULL) {
                id = -1;
                i = cpu_count;
            } else {
                if (current[i]->static_priority < lowestP) {
                    lowestP = current[i]->static_priority;
                    id = i;
                }
            }
        }
        pthread_mutex_unlock(&current_mutex);
        if (id != -1) {
            force_preempt(id);
        }
    }

    pthread_mutex_lock(&ready_q_mutex);
    if (ready_q == NULL) {
        ready_q = process;
    } else {
        pcb_t *last = ready_q;
        while (last->next != NULL) {
            last = last->next;
        }
        process->next = NULL;
        last->next = process;
    }
    pthread_cond_broadcast(&ready_q_cond);
    pthread_mutex_unlock(&ready_q_mutex);
}


/*
 * main() simply parses command line arguments, then calls start_simulator().
 * You will need to modify it to support the -r and -p command-line parameters.
 */
int main(int argc, char *argv[]) {
    /* Parse command-line arguments */
    if (argc < 2 || argc > 4) {
        fprintf(stderr, "CS 2200 Project 5 Fall 2016 -- Multithreaded OS Simulator\n"
                "Usage: ./os-sim <# CPUs> [ -r <time slice> | -p ]\n"
                "    Default : FIFO Scheduler\n"
                "         -r : Round-Robin Scheduler\n"
                "         -p : Static Priority Scheduler\n\n");
        return -1;
    }
    cpu_count = atoi(argv[1]);

    /* FIX ME - Add support for -r and -p parameters*/
    /********** TODO **************/
    if (argc > 2) {
        if (argv[2][1] == 'r') {
            timeslice = atoi(argv[3]);
        }
        if (argv[2][1] == 'p') {
            p = 1;
        }
    }


    /* Allocate the current[] array and its mutex */
    /*********** TODO *************/
    current = malloc(sizeof(pcb_t *) * cpu_count);
    assert(current != NULL);
    pthread_mutex_init(&current_mutex, NULL);
    pthread_mutex_init(&ready_q_mutex, NULL);
    pthread_cond_init(&ready_q_cond, NULL);

    /* Start the simulator in the library */
    start_simulator(cpu_count);

    return 0;
}


