
#include <stdlib.h>
#include <stdio.h>
#include "bstset.h"
typedef struct bnode_t {
	struct bnode_t *left;
	struct bnode_t *right;
	void *data;
} node;

static node *create_node(void *data) {
	node *n = malloc(sizeof(node));
	n->left = NULL;
	n->right = NULL;
	n->data = data;
	return n;
}