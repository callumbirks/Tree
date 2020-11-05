package com.callumbirks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TreeTest {

    private Tree<String> tree1;
    private Tree<String> tree2;
    private Tree<String> tree3;
    private final String childString1 = "childTest1";
    private final String childString2 = "childTest2";
    private Tree<String> child1;
    private Tree<String> child2;
    List<Tree<String>> childList;
    private Tree<String> parentNode;

    @BeforeEach
    public void setUp() {
        tree1 = new Tree<>();   //Tree using the default constructor
        tree2 = new Tree<>("test");     //Tree using the secondary constructor
        child1 = new Tree<>(childString1);
        child2 = new Tree<>(childString2);
        childList = new ArrayList<>();
        childList.add(child1);
        childList.add(child2);
        tree3 = new Tree<>("test",childList);   //Tree using the tertiary constructor
        parentNode = new Tree<>("parentTest");
        parentNode.addChild(tree2);
        parentNode.addChild(tree3);
    }

    @Test
    void defaultConstructorSetsFieldToNull() throws Exception {
        assertEquals(null,tree1.getField(),"Default constructor should set mField to null");
    }

    @Test
    void defaultConstructorChildArrayIsEmpty() throws Exception {
        assertEquals(false,tree1.hasChildren(),"Default constructor should leave mChildren empty");
    }

    @Test
    void secondaryConstructorFieldEqualsParameter() throws Exception {
        assertEquals("test",tree2.getField(),"Secondary constructor should set mField to the parameter passed in");
    }

    @Test
    void secondaryConstructorChildArrayIsEmpty() throws Exception {
        assertEquals(false,tree2.hasChildren(), "Secondary constructor should leave mChildren empty");
    }

    @Test
    void tertiaryConstructorFieldEqualsParameter() throws Exception {
        assertEquals("test",tree3.getField(),"Tertiary constructor should set mField to the first parameter passed in");
    }

    @Test
    void tertiaryConstructorChildArrayEqualsParameter() throws Exception {
        assertEquals(childList, tree3.getChildren(),"Tertiary constructor should set mChildren to the second parameter passed in");
    }

    @Test
    void emptyTreeHasChildrenReturnsFalse() throws Exception {
        assertEquals(false,tree1.hasChildren(),"hasChildren() should return false if mChildren is empty");
    }

    @Test
    void getFieldMethodReturnsMFieldVariable() throws Exception {
        assertEquals("test",tree2.getField(),"getField() should return the mField variable");
    }

    @Test
    void setFieldMethodSetsMFieldToParameter() throws Exception {
        tree1.setField("SetTest");

        assertEquals("SetTest",tree1.getField(),"setField() should set mField to the parameter passed in");
    }

    @Test
    void getChildrenReturnsListOfChildren() throws Exception {
        assertEquals(childList, tree3.getChildren(),"getChildren() should return the list of the current node's children");
    }

    @Test
    void addChildMethodAddsNewChildCorrectly() throws Exception {
        tree1.addChild(child1);
        tree1.addChild(child2);

        assertEquals(childList,tree1.getChildren(),"addChild() should add the specified new node as a child of the current node");
    }

    @Test
    void addChildOverloadAddsChildNodeCorrectly() throws Exception {
        parentNode.addChild(child2.getField(),new Tree<>("childTest"));

        assertEquals(new Tree<>("childTest"),parentNode.searchNode("childTest"));
    }

    @Test
    void addChildOverloadNonExistentValueThrowsException() throws Exception {
        assertThrows(NoSuchObjectException.class, () -> tree3.addChild("invalid", new Tree<>("test")));
    }

    @Test
    void searchNodeReturnsThisObjectIfValueSame() throws Exception {
        assertEquals(tree2,tree2.searchNode("test"),"searchNode() should return the current node if the value passed in matches the current node's mField variable");
    }

    @Test
    void searchNodeReturnsCorrectTree() throws Exception {
        assertEquals(child1,tree3.searchNode(childString1));
    }

    @Test
    void searchNodeReturnsCorrectTreeMultipleLevels() throws Exception {
        assertEquals(child2,parentNode.searchNode(childString2));
    }

    @Test
    void searchNodeNonExistentValueReturnsNull() throws Exception {
        assertEquals(null, tree3.searchNode("invalid"));
    }

    @Test
    void removeChildAtIndexReturnsChild() throws Exception {
        assertEquals(child1,tree3.removeChild(0));
    }

    @Test
    void removeChildAtIndexRemovesChildFromTree() throws Exception {
        List<Tree<String>> child1List = new ArrayList<>();
        child1List.add(child1);
        tree3.removeChild(1);

        assertEquals(child1List,tree3.getChildren());
    }

    @Test
    void removeChildSearchReturnsChild() throws Exception {
        assertEquals(child1, parentNode.removeChild(childString1));
    }

    @Test
    void removeChildSearchNonExistentValueReturnsNull() throws Exception {
        assertEquals(null,tree3.removeChild("invalid"));
    }

    @Test
    void getHeightReturnsCorrectHeight() throws Exception {
        assertEquals(3,parentNode.getHeight(parentNode));
    }
}













