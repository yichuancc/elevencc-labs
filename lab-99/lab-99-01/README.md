# test01：解析数学表达式



> https://blog.csdn.net/G971005287W/article/details/122668122
>
> https://github.com/scireum/parsii

# test02：拖拽排序后台设计与实现

> https://blog.csdn.net/sdujava2011/article/details/135700047

## 前言

项目中有一个需求是拖拽排序，将类似卡片的东西拖拽重新排列
有以下几个需求:

- 允许更改元素的排序
- 允许新增数据，并更新现有排序
- 允许删除数据，并更新现有排序

## 实现思路

### 一、全量更新元素位置法

适用场景：排序[元素数量](https://so.csdn.net/so/search?q=元素数量&spm=1001.2101.3001.7020)较少，对于大量数据排序不适用
原理：每一个元素拥有一个字段，表示元素当前排序的位置，通过前端排序，将排好的元素位置，一次性发送到后端。然后，后端统一更新所有元素的位置
具体实现：
实体设计：增加[排序字段](https://so.csdn.net/so/search?q=排序字段&spm=1001.2101.3001.7020)sort，表示元素当前的位置。例如，sort=1，则表示元素处于第一位
接口设计：

```bash
前端发送id数组，数组的索引表示数组当前的排序位置
例如：[3,2,4,1] 表示id为3的sortId为1，id为4的数组sortId为3
12
```

移动元素：移动元素后前端将id数组发送给后台
删除元素：删除元素直接删除id对应的元素对排序没什么影响，不过可能出现sortId出现跳过某个值的情况。也可以删除前端发过来数组中不存在而服务器端存在的元素，然后根据删除后的数组元素进行全部更新
新增元素：新增元素时如果想要放在最后一位，那么sortId为sortId的最大值加一，如果想要将新增的元素放在第一位，那么将前端把新加的数组放在第一位，发数组id给后端再进行全排序

### 二、取中值法

适用场景：数据量较多，移动次数不是很频繁
原理：新加一个字段sortId，每个字段之间的间隔都很大，例如第一个65536，第一个为2*65536，当改变了某个元素的位置时，只要前端发送这个元素id和想要移动到的位置的id对应的sortId，后台根据拖拽的位置进行更新
具体实现：
1、移动元素：

- 调整一个元素到两个元素之间时，此元素的sortId为调整后位置前后两个元素的sortId相加处于二，即sortId = (pre_sortId + after_sortId)/2
- 调整一个元素到第一个元素，此元素的sortId为第一个元素的一半，即sortId = first_sortId / 2
- 调整一个元素到最后一个元素时，此元素的sortId为最后一个元素加65536，即sortId = old_sortId + 65536

2、删除元素
不影响排序

3、新增元素
创建第一个元素时默认为65536，第二个元素为265536，第N个元素为N65536

注意点:
理论上这种方式可以一直变更顺序，但是考虑到由于精度的限制，所以sortId在某个情况下就可能变得变得很小导致排序失效，所以有两个方法，一是设置一个安全值，当sortId低于这个安全值时就进行全表的重排，二是通过定时全表进行更新

### 三、单表单列

适用场景：快取取出某一位置的元素，查询次数多修改频率少的
每个元素，都有一个字段index，表示元素的排序信息
规定元素从0开始递增。
具体实现：
1、新增元素
新增元素时，序号为当前元素数据总量-1
2、删除元素
删除元素时，将大于该序号的元素index都减一
3、修改元素
前端发送一个偏移量，有正有负，比如从第五移动到第三，那么偏移量就是-2，那么移动的元素的index加上偏移量，然后将移动元素的原位置的偏移量范围内的元素进行加减
例如：
第六移动到第三，偏移量为-3，那么将第三到第五的位置index都加一，将当前元素的index加上偏移量，即5-3=2，在第三个位置上
