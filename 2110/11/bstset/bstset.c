/**
 * CS 2110 - Fall 2015 - HW11
 * Andrew Wilder
 *
 * Name: ***** YOUR NAME HERE !! *****
 * bstset.c: Complete the functions!
 */

#include <stdlib.h>
#include <stdio.h>
#include "bstset.h"

/* The bnode struct. This struct is not visible to the user of the bstset.
 * Do not modify this in any way or else you will get a zero. You have been
 * warned! As a design paradigm, only this file should know about this struct.
 * Do not add this struct definition to other files.
 */
typedef struct bnode_t {
	struct bnode_t *left;
	struct bnode_t *right;
	void *data;
} node;

/* There should be absolutely no global data here or else you are doing
 * something wrong. This library should work for multiple bst sets!
 *
 * However, you absolutely may add helper functions here if you would like.
 * Several of these functions have simple, elegant recursive solutions.
 */

/**
 * create_node
 *
 * Helper function that creates a node by allocating memory for it on the heap,
 * and initializing any pointers in it to NULL.
 *
 * @param data A void pointer to data the user is adding to the bst set.
 * @return The node
 */
static node *create_node(void *data) {
	node *n = malloc(sizeof(node));
	n->left = NULL;
	n->right = NULL;
	n->data = data;
	return n;
}

/**
 * create_bstset
 *
 * Creates a bst set by allocating memory for it on the heap. Be sure to
 * initialize the size to zero, as well as root to NULL.
 *
 * @return An empty bst set
 */
bstset *create_bstset(void) {
	bstset *b = malloc(sizeof(bstset));
	b->size = 0;
	b->root = NULL;
	return b;
}

int addData(node * pcur, void *data,bst_comp comp_func){
	int compare = comp_func(data, pcur->data);
	if(compare > 0){
		if(pcur->right != NULL){
			return addData(pcur->right,data,comp_func);
		} else {
			pcur->right = create_node(data);
			return 1;
		}
	} else if(compare < 0){
		if(pcur->left != NULL){
			return addData(pcur->left,data,comp_func);
		} else {
			pcur->left = create_node(data);
			return 1;
		}
	} else {
		return 0;
	}
}
/**
 * add
 *
 * Adds the data to the bst set in the appropriate sorted location.
 * Use comp_func on data, as well as the data contained in a node to determine
 * what to do. The specification for comp_func is as follows:
 *   comp_func(data1, data2)
 *     data1 > data2: returns a positive number
 *     data1 < data2: returns a negative number
 *     data1 = data2: returns zero
 * If the data we are trying to add is already in the bst set, do not modify
 * the bst set.
 *
 * @param bst The bst set we are adding to.
 * @param data The data we are adding to the bst set.
 * @param comp_func The comparison function for two pieces of data.
 * @return 0 if the data was added, 1 if the data was already in the bst set.
 */
int add(bstset *bst, void *data, bst_comp comp_func) {
	if(bst->root == NULL){
		bst->root = create_node(data);
		bst->size = 1;
		return 0;
	} else {
		int added = addData(bst->root, data, comp_func);
		bst->size += added;
		return (added ^ 1);
	}
}

void *goLeft(node *pcur){
	if(pcur->left != NULL){
		return goLeft(pcur->left);
	} else {
		return pcur-> data;
	}
}
/**
 * min
 *
 * Gets the minimum element of the bst set.
 * Recall that bst structures are such that the value to the left of the
 * current node is less than the current node. This means that the minimum
 * value should be contained all the way down the left branch of the bst set.
 *
 * @param bst The bst set.
 * @return The minimum element, or NULL if nothing in the bst set
 */
void *min(bstset *bst) {
	if(bst->root == NULL){
		return NULL;
	} else if(bst->root->left == NULL){
		return bst->root->data;
	} else {
		return goLeft(bst->root);
	}
	return NULL;
}

void *goRight(node *pcur){
	if(pcur->right != NULL){
		return goRight(pcur->right);
	} else {
		return pcur-> data;
	}
}
/**
 * max
 *
 * Gets the maximum element of the bst set.
 * Recall that bst structures are such that the value to the right of the
 * current node is greater than the current node. This means that the maximum
 * value should be contained all the way down the right branch of the bst set.
 *
 * @param bst The bst set.
 * @return The maximum element, or NULL if nothing in the bst set
 */
void *max(bstset *bst) {
	if(bst->root == NULL){
		return NULL;
	} else if(bst->root->right == NULL){
		return bst->root->data;
	} else {
		return goRight(bst->root);
	}
	return NULL;
}

int find(node *pcur, void *data, bst_comp comp_func){
	int compare = comp_func(data, pcur->data);
	if(compare > 0){
		if(pcur->right == NULL){
			return 0;
		} else {
			return find(pcur->right,data,comp_func);
		}
	} else if (compare < 0){
		if(pcur->left == NULL){
			return 0;
		} else {
			return find(pcur->left,data,comp_func);
		}
	}else {
		return 1;
	}
}
/**
 * contains
 *
 * Tells if the given data is contained in the bst set.
 * Use comp_func to determine if the given data is equal to something in the
 * bst set. You should not traverse the entire bst to find the data, because it
 * is a sorted structure! Any node should only be visited once in trying to
 * find the data.
 *
 * @param bst The bst set.
 * @param comp_func The comparison function for two pieces of data.
 * @return 1 if the data was in the bst set, 0 otherwise.
 */
int contains(bstset *bst, void *data, bst_comp comp_func) {
	if(bst->root != NULL){
		return find(bst->root, data, comp_func);
	}
	return 0;
}

void inOrder(node *pcur, bst_op do_func){
	if(pcur->left != NULL){
		inOrder(pcur->left,do_func);
	}
	do_func(pcur->data);
	if(pcur->right != NULL){
		inOrder(pcur->right, do_func);
	}
}
/**
 * traverse
 *
 * Do something to each piece of data in the bst.
 * The traversal method you should use is "in-order" traversal:
 *   Recurse left (if there is a left)
 *   Do something to current node's data
 *   Recurse right (if there is a right)
 *
 * The effect of processing the data is in this order:
 *       _4_
 *      /   \
 *     2     6
 *    / \   / \
 *   1   3 5   7
 *
 * @param bst The bst set.
 * @param do_func The function to perform on each piece of data.
 */
void traverse(bstset *bst, bst_op do_func) {
	if(bst->root!= NULL){
		inOrder(bst->root,do_func);
	}
}


void levelOrderFree(node *pcur, bst_op free_func){
	if(pcur->left != NULL){
		levelOrderFree(pcur->left,free_func);
	}
	if(pcur->right != NULL){
		levelOrderFree(pcur->right, free_func);
	}
	free_func(pcur->data);
	free(pcur);
}
/**
 * destroy
 *
 * Destroy the bst set. Everything in the bst set including bstset structure,
 * nodes and data should all be freed from the heap.
 * Since the only one who knows how to free the data is the user, you must use
 * free_func (which is written by the user) to free the data, instead of
 * regular free. However, regular free will suffice for the bst structure and
 * nodes since they were created with malloc.
 *
 * @param bst The bst set.
 * @param free_func The function that the user created to free the data.
 */
void destroy(bstset *bst, bst_op free_func) {
	if(bst->root != NULL){
		levelOrderFree(bst->root,free_func);
	}
	free(bst);
}

