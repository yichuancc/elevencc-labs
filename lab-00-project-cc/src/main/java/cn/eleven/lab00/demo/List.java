package cn.eleven.lab00.demo;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-09-16 16:40
 */
public interface List<E> {
    boolean add(E e);

    boolean addFirst(E e);

    boolean addLast(E e);

    boolean remove(Object o);

    E get(int index);

    void printLinkList();
}
