class Link<T>{
    private class Node {
        private T data;
        private Node next;
        public Node(T data) {
            this.data = data;
        }

        /**
         *设置新节点保存，所有新节点保存在最后一个节点之后
         *newNode新节点对象
         */
        public void addNode(Node newNode) {
            if (this.next == null) {
                this.next = newNode;
            } else {
                this.next.addNode(newNode);
            }
        }

        /**
         * 数据检索操作，判断指定数据是否存在
         * 第一次调用(Link):this=Link.root
         * 第二次调用(Node):this=Link.root.next
         * data 要查询的数据
         * 如果数据存在返回true，否则返回false
         */
        public boolean containsNode(T data) {
            if (data.equals(this.data)) {//如果这是泛型就要先判断数据类型
                return true;
            } else {
                if (this.next != null) {
                    return this.next.containsNode(data);
                } else {
                    return false;
                }
            }
        }

        public T getNode(int index) {
            //使用当前的foot内容与要查询的索引进行比较，随后将foot的内容自增，目的是方便下次查询
            if (Link.this.foot++ == index) {
                return this.data;
            } else {
                return this.next.getNode(index);
            }
        }

        /**
         *修改指定索引节点包含的数据
         *index 要修改的索引编号
         *data 新数据
         */
        public void setNode(int index, T data) {
            //使用当前的foot内容与要查询的索引进行比较，随后将foot的内容自增，目的是方便下次查询
            if (Link.this.foot++ == index) {
                this.data = data;
            } else {
                this.next.setNode(index, data);
            }
        }

        /**
         *节点的删除操作，匹配每一个节点的数据，如果当前节点数据都符合删除数据
         *则使用“当前节点上一节点next = 当前节点.next”方式空出当前节点
         *第一次调用(Link):previous = Link.root、this = Link.root.next
         *第二次调用(Node):previous = Link.root.next、this = Link.root.next.next
         *previous 当前节点的上一个节点
         *data 要删除的数据
         */
        public void removeNode(Node previous, T data) {
            if (data.equals(this.data)) {
                previous.next = this.next;
            } else {
                this.next.removeNode(this, data);
            }
        }
    }
    private Node root;
    private int count = 0;
    private int foot = 0;
    /**
     *用户向链表增加新的数据，在增加时要将数据封装为Node类，这样才可以匹配节点吮吸
     *data要保存的数据
     */
    public void add(T data){        //假设不允许有null
        if(data == null){                //判断数据是否为空
            return;                      //结束方法调用
        }
        Node newNode = new Node(data);   //要保存的数据
        if(this.root==null){             //当前没有根节点
            this.root = newNode;         //保存根节点
        }else{                           //根节点存在
            this.root.addNode(newNode);  //交给Node类处理节点的保存
        }
    }
    /**
     *取得链表中保存的数据个数
     *返回保存的个数，通过count属性取得
     */
    public int size(){
        return this.count;
    }
    /**
     *判断是否为空链表，表示长度为0，不是null
     *如果链表中没有保存任何数据则返回true，否则返回false
     */
    public boolean isEmpty(){
        return this.count == 0;
    }
    /**
     *数据查询操作，判断指定数据是否存在，如果链表没有数据就直接返回false
     *data 要查询的数据
     *如果数据存在返回true，否则返回false
     */
    public boolean contains(T data){
        if(data == null ||this.root == null){
            return false;
        }
        return this.root.containsNode(data);
    }
    /**
     *根据索引取得保存的节点数据
     *index 索引数据
     *如果要取得的索引内容不存在或者大于保存个数返回null，反之返回数据
     */
    public T get(int index){
        if(index > this.count){
            return null;
        }
        this.foot = 0;
        return this.root.getNode(index);
    }
    /**
     *根据索引修改数据
     *index 要修改数据的索引编号
     *data 新的数据类型
     */
    public void set(int index,T data){
        if(index > this.count){
            return;
        }
        this.foot = 0;
        this.root.setNode(index,data);
    }
    /**
     *链表数据的删除操作，在删除前要先使用contains()判断链表中是否存在指定数据
     *如果要删除的数据存在，则首先要判断根节点的数据是否为要删除数据
     *如果是，则将根节点的下一个节点作为新的根节点
     *如果要删除的数据不是根节点数据，则将删除操作交由Node类的removeNode()方法完成
     *data要删除的数据
     */
    public void remove(T data){
        if(this.contains(data)){
            //要删除数据是否是根节点数据，root是Node类的对象，此处直接访问内部类的私有操作
            if(data.equals(this.root.data)){
                this.root = this.root.next;
            }else{
                //此时根元素已经判断过了，从第二个元素开始判断，即第二个元素的上一个元素为根节点
                this.root.next.removeNode(this.root,data);
            }
            this.count--;
        }
    }

    public void clear(){
        this.root = null;
        this.count = 0;
    }
}

