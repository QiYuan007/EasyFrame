package demo.myframework.db.dao;

/**
 * @Author: lizhipeng
 * @Data: 16/5/26 上午10:18
 * @Description: 数据库建表的基类模型
 */

import org.xutils.db.annotation.Column;

/**
 * 1.在类名上面加入@Table标签，标签里面的属性name的值就是以后生成的数据库的表的名字
 * 2.实体bean里面的属性需要加上@Column标签，这样这个标签的name属性的值会对应数据库里面的表的字段。
 * 3.实体bean里面的普通属性，如果没有加上@Column标签就不会在生成表的时候在表里面加入字段。
 * 4.实体bean中必须有一个主键，如果没有主键，表以后不会创建成功
 * ,@Column(name=”id”,isId=true,autoGen=true)
 * 这个属性name的值代表的是表的主键的标识，
 * isId这个属性代表的是该属性是不是表的主键，
 * autoGen代表的是主键是否是自增长，如果不写autoGen这个属性，默认是自增长的属性。
 */
public class BaseDb {
    @Column(name = "id", isId = true, autoGen = true)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
