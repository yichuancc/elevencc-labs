package cn.eleven.lab00.demo;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-09-16 16:34
 */
public class LinkedList<E> implements List<E> {
    transient int size = 0;

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean addFirst(E e) {
        return false;
    }

    @Override
    public boolean addLast(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public void printLinkList() {

    }
}
