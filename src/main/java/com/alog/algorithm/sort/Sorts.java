package com.alog.algorithm.sort;

/**
 * 各种排序算法的实现<br/>
 * 1.Bubble 冒泡排序<br/>
 * 2.Selection 选择排序<br/>
 * 3.Insertion 插入排序<br/>
 * 4.Shell 希尔排序 <br/>
 * 5.Merge 归并排序<br/>
 * 6.Quick 快速排序<br/>
 * 7.Heap 堆排序
 *
 * @author lushenchen 2023/11/29 19:53
 * @since 1.0.0
 */
public enum Sorts
{
    /**
     * 冒泡排序<br/>
     * 1.比较相邻的元素。如果前一个元素比后一个元素大，就交换这两个元素的位置。<br/>
     * 2.对每一对相邻元素做同样的工作，从开始第一对元素到结尾的最后一对元素。最终最后位置的元素就是最大值。
     */
    Bubble
            {
                @Override
                public void sort(Comparable[] arr)
                {
                    if (!Bubble.validate(arr))
                    {
                        return;
                    }
                    int length = arr.length;
                    // 数据中参与冒泡的元素个数
                    for (int i = length - 1; i > 0; i--)
                    {
                        // 从0开始，到小于当前元素个数的位置，两两比较两个元素的大小
                        for (int j = 0; j < i; j++)
                        {
                            // 如果前面比后面大，则交换两个位置的数据
                            if (Bubble.greater(arr[j], arr[j + 1]))
                            {
                                Bubble.exchange(arr, j, j + 1);
                            }
                        }
                    }
                }
            },
    /**
     * 选择排序<br/>
     * 1.第一次从待排序的数据元素中选出最小（或最大）的一个元素，存放在序列的起始位置，
     * 然后再从剩余的未排序元素中寻找到最小（大）元素，然后放到已排序的序列的末尾。<br/>
     * 2.以此类推，直到全部待排序的数据元素的个数为零。选择排序是不稳定的排序方法。
     */
    Selection
            {
                @Override
                public void sort(Comparable[] arr)
                {
                    if (!Selection.validate(arr))
                    {
                        return;
                    }
                    int length = arr.length;
                    // 需要 （数组长度 - 1）次排序
                    for (int i = 0; i < length - 1; i++)
                    {
                        // 假定当前位置的数组为最小索引
                        int minIndex = i;
                        for (int j = i + 1; j < length; j++)
                        {
                            // 从当前位置开始，比较两个数的大小，记录较小的位置索引
                            if (Selection.greater(arr[minIndex], arr[j]))
                            {
                                minIndex = j;
                            }
                        }
                        // 交换两个位置的数组
                        Selection.exchange(arr, i, minIndex);
                    }
                }
            },
    /**
     * 插入排序<br/>
     */
    Insertion
            {
                @Override
                public void sort(Comparable[] arr)
                {
                    if (!Insertion.validate(arr))
                    {
                        return;
                    }
                    int length = arr.length;
                    // 将数组分为已排序和未排序两个部分，并将数据插入到前面已经排序的数组中去
                    for (int i = 1; i < length; i++)
                    {
                        // 从元素j开始向前插入当前元素
                        for (int j = i; j > 0; j--)
                        {
                            if (Insertion.less(arr[j], arr[j - 1]))
                            {
                                Insertion.exchange(arr, j, j - 1);
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                }
            },
    /**
     * 希尔排序(缩小增量排序)
     */
    Shell
            {
                @Override
                public void sort(Comparable[] arr)
                {
                    if (!Shell.validate(arr))
                    {
                        return;
                    }
                    int length = arr.length;
                    // 增量h
                    int h = 1;
                    // 选定一个增量，使得当前 h > length / 2 的最小值
                    while (h < length / 2)
                    {
                        h = 2 * h + 1;
                    }
                    // 只要增量 h >= 1，继续执行，同时缩小增量 h = h / 2;
                    while (h >= 1)
                    {
                        // 从h增量出分割数组，并将每组中的元素从h开始进行插入排序
                        for (int i = h; i < length; i++)
                        {
                            // 以当前位置为起始位置，进行插入排序
                            for (int j = i; j >= h; j -= h)
                            {
                                if (Shell.less(arr[j], arr[j - h]))
                                {
                                    Shell.exchange(arr, j, j - h);
                                }
                                else
                                {
                                    break;
                                }
                            }
                        }
                        // 使得增量持续缩小
                        h = h / 2;
                    }
                }
            },
    /**
     * 归并排序
     */
    Merge
            {
                @Override
                public void sort(Comparable[] arr)
                {
                    if (!Merge.validate(arr))
                    {
                        return;
                    }
                    int left = 0, right = arr.length - 1;
                    sort(arr, left, right);
                }
                
                /**
                 * 对一个数组中指定位置（从索引lo到hi）的元素进行排序
                 * @param arr 待排序数组
                 * @param left 左索引
                 * @param right 右索引
                 */
                private void sort(Comparable[] arr, int left, int right)
                {
                    if (left >= right)
                    {
                        return;
                    }
                    // 找出数组的中间位置，防止整数溢出
                    int mid = left + (right - left) / 2;
                    // 分左子组
                    // System.out.println("Sor(" + Arrays.toString(arr) + ", " + left + ", " + right + ")");
                    sort(arr, left, mid);
                    // System.out.println("Sor(" + Arrays.toString(arr) + ", " + left + ", " + mid + ")");
                    // 分右子组
                    sort(arr, mid + 1, right);
                    // 合并并排序
                    // System.out.println("Meger(" + Arrays.toString(arr) + ", " + left + ", " + mid + ", " + right + ")");
                    merge(arr, left, mid, right);
                    
                }
                
                /**
                 * 对数组中，从lo到mid为一组，从mid+1到hi为一组，对这两组有序数据进行归并
                 * @param arr 待归并的数组
                 * @param left 左索引
                 * @param mid 中间索引
                 * @param right 右索引
                 */
                private void merge(Comparable[] arr, int left, int mid, int right)
                {
                    // 临时数组指针
                    int index = 0;
                    // p1 左子组指针  p2 右子组的指针
                    int p1 = left, p2 = mid + 1;
                    // 临时数组
                    Comparable[] temp = new Comparable[right - left + 1];
                    // 使用双指针遍历排序
                    while (p1 <= mid && p2 <= right)
                    {
                        if (Merge.less(arr[p1], arr[p2]))
                        {
                            temp[index++] = arr[p1++];
                        }
                        else
                        {
                            temp[index++] = arr[p2++];
                        }
                    }
                    // 排查数组中的元素是否全部排序完毕
                    while (p1 <= mid)
                    {
                        temp[index++] = arr[p1++];
                    }
                    while (p2 <= right)
                    {
                        temp[index++] = arr[p2++];
                    }
                    // 将临时有序的数组中的元素替换原数组
                    for (int i = 0; i < temp.length; i++)
                    {
                        arr[left++] = temp[i];
                    }
                }
            },
    /**
     * 快速排序
     */
    Quick
            {
                @Override
                public void sort(Comparable[] arr)
                {
                    if (!Quick.validate(arr))
                    {
                        return;
                    }
                    int length = arr.length;
                    sort(arr, 0, length - 1);
                }
                
                /**
                 * 对数组中left到right的位置进行排序
                 * @param arr 待排序数组
                 * @param left 左边界
                 * @param right 右边界
                 */
                private void sort(Comparable[] arr, int left, int right)
                {
                    // 边界条件检查
                    if (left >= right)
                    {
                        return;
                    }
                    // 获取分组界限位置，分割数组，并将左右子组进行分别排序
                    int partition = partition(arr, left, right);
                    // 左子组排序
                    sort(arr, left, partition - 1);
                    // 右子组排序
                    sort(arr, partition + 1, right);
                }
                
                /**
                 * 在数组中选定一个元素，并将大于该元素的值放在其右侧，
                 * 小于该元素的值放在其右边，并返回该元素调整之后的位置
                 * @param arr 待分割的数组
                 * @param left 左边界
                 * @param right 右边界
                 * @return 返回分割边界的索引值
                 */
                private int partition(Comparable[] arr, int left, int right)
                {
                    // 取left位置的数组元素作为分割的基准元素值
                    Comparable key = arr[left];
                    // 定义两个指针分别指向left位置和(right + 1)位置
                    int pl = left;
                    int pr = right + 1;
                    while (true)
                    {
                        // 向左侧移动右指针，直到寻找到比 key 小的元素，然后停止
                        while (true)
                        {
                            // 边界条件
                            if (pr == left || Quick.greater(key, arr[--pr]))
                            {
                                break;
                            }
                        }
                        // 向右移动左指针，直到寻找到比key大的元素，然后停止
                        while (true)
                        {
                            // 边界条件
                            if (pl == right || Quick.less(key, arr[++pl]))
                            {
                                break;
                            }
                        }
                        
                        // 检测边界条件
                        if (pl >= pr)
                        {
                            break;
                        }
                        else
                        {
                            // 交换左右两个位置元素的值
                            Quick.exchange(arr, pl, pr);
                        }
                    }
                    // 交换基准值和right指针位置的元素，因为right指向的元素肯定是比基准值小的元素
                    Quick.exchange(arr, left, pr);
                    return pr;
                }
            },
    Heap
            {
                public void sort(Comparable[] arr)
                {
                    if (!Heap.validate(arr))
                    {
                        return;
                    }
                    // 辅助数组，构建堆
                    Comparable[] asst = new Comparable[arr.length + 1];
                    System.arraycopy(arr, 0, asst, 1, arr.length);
                    // 构建大顶堆
                    buildMaxHeap(asst);
                    // 循环删除堆顶元素，并将剩余的元素重新构建大顶堆
                    for (int i = 1; i < asst.length; i++)
                    {
                        System.out.print(asst[i] + " ");
                    }
                    System.out.println();
                    // 通过不断移除堆顶元素，将剩余的元素重新调整成大顶堆
                    for (int i = asst.length - 1; i > 0; i--)
                    {
                        // 将堆顶元素与最后一个元素交换，然后将堆元素重新调整成大顶堆
                        Heap.exchange(asst, 1, i);
                        sink(asst, 1, i - 1);
                    }
                    System.arraycopy(asst, 1, arr, 0, asst.length - 1);
                    
                }
                
                /**
                 * 将现有数组的元素构建成一个大顶堆
                 * @param heap 乱序的数组
                 * @return
                 */
                private void buildMaxHeap(Comparable[] heap)
                {
                    // 非叶子节点下沉，直到根节点
                    for (int i = (heap.length) / 2; i >= 1; i--)
                    {
                        sink(heap, i, heap.length - 1);
                    }
                }
                
                /**
                 * 通过下沉算法，对堆中位于target的元素做下沉操作，范围是0-range
                 * @param heap
                 * @param target
                 * @param range
                 */
                private void sink(Comparable[] heap, int target, int range)
                {
                    // 完全二叉树的性质，如果需要下沉，边界则是需要保证有左子树
                    while (2 * target <= range)
                    {
                        int max;
                        // 如果没有右子树
                        if (2 * target + 1 > range)
                        {
                            max = 2 * target;
                        }
                        else
                        {
                            // 取左右子树的最大值
                            if (Heap.greater(heap[2 * target], heap[2 * target + 1]))
                            {
                                max = 2 * target;
                            }
                            else
                            {
                                max = 2 * target + 1;
                            }
                        }
                        // 如果当前节点大于左右子树的最大值，则退出循环
                        if (Heap.greater(heap[target], heap[max]))
                        {
                            break;
                        }
                        Heap.exchange(heap, target, max);
                        target = max;
                    }
                }
                
            };
    
    /**
     * 对指定的数组进行排序
     *
     * @param arr 待排序的数组
     */
    public void sort(Comparable[] arr)
    {
        throw new UnsupportedOperationException("不支持的操作~");
    }
    
    /**
     * 有效性检测，如果合法，返回true
     *
     * @param arr 待检测的数组
     * @return
     */
    private boolean validate(Comparable[] arr)
    {
        if (arr == null || arr.length <= 1)
        {
            return false;
        }
        return true;
    }
    
    /**
     * 在数组arr中，交换位置i和位置j的元素值
     *
     * @param arr 指定的数组
     * @param i   索引i
     * @param j   索引j
     */
    private void exchange(Comparable[] arr, int i, int j)
    {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * 比较两个数据的大小，前者是否大于后者
     *
     * @param c1 数据1
     * @param c2 数据2
     * @return
     */
    private boolean greater(Comparable c1, Comparable c2)
    {
        return c1.compareTo(c2) > 0;
    }
    
    /**
     * 比较两个数据的大小，前者是否大于后者
     *
     * @param c1 数据1
     * @param c2 数据2
     * @return
     */
    private boolean less(Comparable c1, Comparable c2)
    {
        return c1.compareTo(c2) < 0;
    }
}