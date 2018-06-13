/**
 * 基于Java数组二次封装的动态数组
 */
public class Array<E> {

    // 私有，用户无法从外部访问data，size，保证安全性
    private E[] data;
    private int size;

    // Array构造函数, 传入数组容量capacity
    public Array(int capacity) {
        // java历史遗留问题，不能直接new E[]
        data = (E[]) new Object[capacity];
        size = 0;
    }

    // 无参构造函数，默认capacity=10
    public Array() {
        this(10);
    }

    // 获取Array长度
    public int getSize() {
        return size;
    }

    // 获取Array容量
    public int getCapacity() {
        return data.length;
    }

    // 判断Array是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 向末尾添加元素
    public void addLast(E e) {
//        if (size == data.length) {
//            throw new IllegalArgumentException("AddLast fail, it's full");
//        }
//        data[size] = e;
//        size++;

        // 改为复用add
        add(size, e);
    }

    // 向头部添加元素
    public void addFirst(E e) {
        add(0, e);
    }

    // 向特定位置添加元素
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add fail, Require index >= 0 and index <= size");
        }
        if (size == data.length) {
            // 数组空间扩容（*2）
            resize(2 * data.length);
        }
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++;
    }

    // 取出index位置元素, 用户永远无法访问未使用的空间
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get failed. Index is illegal");
        }
        return data[index];
    }

    public E getLast() {
        return get(size - 1);
    }

    public E getFirst() {
        return get(0);
    }

    // 修改index位置元素为e
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Set failed. Index is illegal");
        }
        data[index] = e;
    }

    // 判断包含
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i] == e) {
                return true;
            }
        }
        return false;
    }

    // 查找元素，返回索引，失败返回 -1
    public int find(E e) {
        for (int i = 0; i < size; i++) {
            // equals是值比较  ==是引用比较
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    // 删除index位置元素，返回被删除的元素
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Remove failed. Index is illegal");
        }
        E ret = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        // 让size指的位置为null（这句也可不写）
        // tips: loitering objects != memory leak
        data[size] = null;
        // Lazy策略, 只用1/4时再缩容一半，提升性能，防止复杂度震荡
        if (size == data.length / 4 && data.length / 2 != 0) {
            resize(data.length / 2);
        }
        return ret;
    }

    // 删除第一个元素，返回被删除的元素
    public E removeFirst() {
        return remove(0);
    }

    // 删除最后一个元素，返回被删除的元素
    public E removeLast() {
        return remove(size - 1);
    }

    // 删除某个元素，返回是否成功删除bool
    public boolean removeElement(E e) {
        int index = find(e);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    // TODO removeAllElement  findAll

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d , capacity = %d\n", size, data.length));
        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1) {
                res.append(", ");
            }
        }
        res.append(']');
        return res.toString();
    }

    // 调整数组容量
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
}
