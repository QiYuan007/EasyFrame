package demo.myframework.db.dao;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * @Author: lizhipeng
 * @Data: 16/5/26 上午10:23
 * @Description:    测试数据库类
 */
@Table(name="person")
public class TestUserDao extends BaseDb{
    //姓名
    @Column(name="name")
    private String name;

    //年龄
    @Column(name="age")
    private int age;

    //性别
    @Column(name="sex")
    private String sex;

    //工资
    @Column(name="salary")
    private String salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "PersonTable [id=" + id + ", name=" + name + ", age=" + age
                + ", sex=" + sex + ", salary=" + salary + "]";
    }
}
