package com.callumbirks;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Generic implementation of a tree.
 * The tree has one field of generic type T, and
 * another private variable which stores an <tt>ArrayList</tt>
 * of its children, each element being of type Tree<T>.
 *
 * @author callumbirks
 *
 * @param <T> The generic type T which determines the
 *           type of the <tt>mField</tt> variable.
 */
public class Tree<T> {
    /**
     * The single field variable of each node on the tree,
     * of type T.
     */
    private T mField;
    /**
     * The list which stores all of the current node's children,
     * populated using an <tt>ArrayList</tt>. Each child is of type <tt>Tree<T></tt>.
     */
    private List<Tree<T>> mChildren;

    /**
     * Default constructor.
     * Sets the <tt>mField</tt> variable to null.
     * Sets the <tt>mChildren</tt> variable to an
     * empty <tt>ArrayList</tt>.
     */
    public Tree() {
        this.mField = null;
        this.mChildren = new ArrayList<>();
    }

    /**
     * Secondary constructor.
     * Sets the <tt>mField</tt> variable to the parameter that was passed
     * into the constructor.
     * Sets the <tt>mChildren</tt> variable to an
     * empty <tt>ArrayList</tt>.
     * @param field The value that <tt>mField</tt> will be initialised with, of generic type T.
     */
    public Tree(T field) {
        this.mField = field;
        this.mChildren = new ArrayList<>();
    }

    /**
     * Tertiary constructor.
     * Sets the <tt>mField</tt> variable to the first parameter that was passed
     * into the constructor.
     * Sets the <tt>mChildren</tt> variable to second parameter passed into the
     * constructor.
     * @param field The value that <tt>mField</tt> will be initialised with, of generic type T.
     * @param children The value that <tt>mChildren</tt> will be initialised with, of type <tt>List<Tree<T>></tt>.
     */
    public Tree(T field, List<Tree<T>> children) {
        this.mField = field;
        this.mChildren = new ArrayList<>(children);
    }

    /**
     * A function which simply checks whether the current node has any child nodes,
     * by checking whether the <tt>mChildren</tt> variable is null.
     * @return A boolean that is true if the current node has children, otherwise false.
     */
    public boolean hasChildren() {
        return (this.mChildren.size() > 0);
    }

    /**
     * A function which simply returns the <tt>mField</tt> variable.
     * @return the <tt>mField</tt> variable.
     */
    public T getField() {
        return this.mField;
    }

    /**
     * A function which sets the <tt>mField</tt> variable to the parameter
     * @param field The value to set the <tt>mField</tt> variable to.
     */
    public void setField(T field) {
        this.mField = field;
    }

    /**
     * A function which returns a list of the children of this node.
     * If this node has no children, returns null.
     * @return <tt>mChildren</tt>, a list of the children of this node,
     * or null if mChildren is empty.
     */
    public List<Tree<T>> getChildren() {
        if(this.hasChildren())
            return this.mChildren;
        return null;
    }

    /**
     * Add a new child under the current node.
     * @param newChild The new node to add as a child of the current node.
     */
    public void addChild(Tree<T> newChild) {
        mChildren.add(newChild);
    }

    /**
     * An overload of the above function. This function uses the {@link #searchNode(Object) searchNode}
     * function to locate a node with an <tt>mField</tt> variable equal to the destParentField parameter.
     * It then adds the newChild parameter as a child on the located node.
     * If a node with the given value cannot be found, <tt>searchNode</tt> returns null,
     * and this function throws a NoSuchObjectException.
     * @param destParentField The <tt>mField</tt> variable of the parent node you wish to add a child to.
     * @param newChild A <tt>Tree<T></tt> representing the new child node.
     */
    public void addChild(T destParentField, Tree<T> newChild) throws NoSuchObjectException {
        Tree<T> parentNode = searchNode(destParentField);
        if(parentNode == null)
            throw new NoSuchObjectException("A node with the provided value does not exist on the tree.");
        parentNode.addChild(newChild);
    }

    /**
     * Searches for a node with an <tt>mField</tt> variable equal to the
     * <tt>searchValue</tt> parameter. The search is completed using
     * recursion and is a depth-first pre-order traversal.
     * If the search value is found, returns an object of type <tt>Tree<T></tt>,
     * which is just a pointer therefore can be used to directly modify the node.
     * If a node does not exist with the given value, the function returns null.
     * @param searchValue The value to search for in the tree
     * @return If node exists with given value, return that node, otherwise return null.
     */
    public Tree<T> searchNode(T searchValue) {
        Tree<T> locatedNode = null;

        if(this.getField().equals(searchValue))
            locatedNode = this;

        for(Tree<T> c : mChildren) {
            if(c.getField().equals(searchValue))
                return c;
            else
                locatedNode = c.searchNode(searchValue);
        }
        return locatedNode;
    }

    /**
     * Removes a child from the current node at the given index.
     * @param index The index of the child you wish to remove from
     *              the <tt>mChildren</tt> list of the current node
     * @return The child of type Tree<T> which has been removed from the node
     * @throws IndexOutOfBoundsException The {@link ArrayList#remove(int) ArrayList(int index)} method will throw this exception if the
     * index provided is out of the bounds of the array, and so this function will also throw the same exception if this occurs.
     */
    public Tree<T> removeChild(int index) throws IndexOutOfBoundsException {
        return mChildren.remove(index);
    }

    /**
     * An overload of the above function. Rather than removing the child at a given index
     * from the current node, it searches across the whole tree for a child node with an
     * <tt>mField</tt> variable of the given value, and then removes it.
     * Returns the removed node if it was found, otherwise returns null.
     * @param searchValue The <tt>mField</tt> variable of the child node you are looking to remove.
     * @return The removed node, of type <tt>Tree<T></tt>. If the node searched for was not found, returns null.
     */
    public Tree<T> removeChild(T searchValue) {
        Tree<T> locatedNode = null;
        for(Tree<T> c : mChildren) {
            if(c.getField().equals(searchValue))
                locatedNode = this.removeChild(this.getChildren().indexOf(c));
            else
                locatedNode = c.removeChild(searchValue);
        }
        return locatedNode;
    }

    /**
     * Find the height of the tree starting from the given node of type <tt>Tree<T></tt>,
     * using recursion and the {@link java.lang.Math#max(int, int) Math.max} method.
     * @param n The node to begin counting the height from.
     * @return The height of the tree from the starting node down to the deepest node.
     */
    public int getHeight(Tree<T> n) {
        int currentMax = 0;
        if(n == null)
            return 0;
        if(n.hasChildren()) {
            for (Tree<T> c : n.getChildren()) {
                currentMax = Math.max(currentMax, getHeight(c));
            }
        }
        return (currentMax + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree<?> tree = (Tree<?>) o;
        return Objects.equals(mField, tree.mField) &&
                Objects.equals(mChildren, tree.mChildren);
    }
    @Override
    public int hashCode() {
        return Objects.hash(mField, mChildren);
    }

    @Override
    public String toString() {
        return "Tree{" +
                "mField='" + mField +
                "', mChildren=" + mChildren +
                '}';
    }
}
