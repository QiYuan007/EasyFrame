package demo.myframework.db;
/**
 * @Author: lizhipeng
 * @Data: 16/5/25 下午4:31
 * @Description: 基于xutils的数据库封装类
 */


import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import demo.myframework.db.dao.BaseDb;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DBHelper {
    private DbManager.DaoConfig mDaoConfig;
    private DbManager mDbManager;

    private DBHelper() {
        mDaoConfig = new DbManager.DaoConfig()
                .setDbName("eztdb.db")
                // 不设置dbDir时, 默认存储在app的私有目录.
//                .setDbDir(new File("/sdcard")) // "sdcard"的写法并非最佳实践
                .setDbVersion(1)
                .setAllowTransaction(true)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: 数据库版本更新后的回调
                    }
                });
        mDbManager = x.getDb(mDaoConfig);
    }


    /**
     * 单例控制器
     */


    private static class SingletonHolder {
        private static final DBHelper INSTANCE = new DBHelper();
    }

    /**
     * 获取DbManager实例
     *
     * @return
     */


    public DbManager getDbManager() {
        return mDbManager;
    }

    /**
     * 获取单例对象
     *
     * @return
     */


    public static DBHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 保存实体类或实体类的List到数据库
     *
     * @param entity
     */
    public void save(final Object entity) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.save(entity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 保存或更新实体类或实体类的List到数据库, 根据id对应的数据是否存在.
     *
     * @param entity
     */


    public void saveOrUpdate(final Object entity) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.saveOrUpdate(entity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 保存实体类或实体类的List到数据库,
     * 如果该类型的id是自动生成的, 则保存完后会给id赋值.
     *
     * @param entity
     */


    public void saveBindingId(final Object entity) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.saveOrUpdate(entity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 在对应表中添加一列,
     * 新的entityType中必须定义了这个列的属性.
     *
     * @param entityType
     * @param column     列名
     * @throws DbException
     */
    public <T extends BaseDb> void addColumn(final Class<T> entityType, final String column) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.addColumn(entityType, column);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 保存或更新实体类或实体类的List到数据库, 根据id和其他唯一索引判断数据是否存在.
     *
     * @param entity
     */

    public void replace(final Object entity) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.replace(entity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 该方法主要是根据表的主键进行单条记录的删除
     *
     * @param entityType
     * @param idValue
     */
    public <T extends BaseDb> void deleteById(final Class<T> entityType, final Object idValue) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.deleteById(entityType, idValue);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 该方法主要是根据实体bean进行对表里面的一条数据进行删除
     *
     * @param entity
     */


    public void delete(final Object entity) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.delete(entity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 该方法主要是用来删除表格里面的所有数据，但是注意：表还会存在，只是表里面数据没有了
     *
     * @param entityType
     */


    public <T extends BaseDb> void delete(final Class<T> entityType) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.delete(entityType);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 该方法主要是根据where语句的条件进行删除操作
     *
     * @param entityType
     * @param whereBuilder
     */


    public <T extends BaseDb> void delete(final Class<T> entityType, final WhereBuilder whereBuilder) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.delete(entityType, whereBuilder);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 删除表
     *
     * @param entityType
     * @throws DbException
     */


    public <T extends BaseDb> void dropTable(final Class<T> entityType) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.dropTable(entityType);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    ///////////// find

    /**
     * 通过主键的值来进行查找表里面的数据
     *
     * @param entityType
     * @param idValue
     * @param <T>
     * @return
     */


    public <T extends BaseDb> Subscription findById(final Class<T> entityType, final Object idValue, Observer<T> observer) {
        Observable<T> observable = Observable.fromCallable(new Callable<T>() {

            @Override
            public T call() {
                try {
                    return mDbManager.findById(entityType, idValue);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subscription;
    }

    /**
     * 该方法主要是返回当前表里面的第一条数据
     *
     * @param entityType
     * @param <T>
     * @return
     */


    public <T extends BaseDb> Subscription findFirst(final Class<T> entityType, Observer<T> observer) {
        Observable<T> observable = Observable.fromCallable(new Callable<T>() {

            @Override
            public T call() {
                try {
                    return mDbManager.findFirst(entityType);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subscription;
    }

    /**
     * 该方法主要是返回当前表里面的所有数据
     *
     * @param entityType
     * @param <T>
     * @return
     */

    public <T extends BaseDb> Subscription findAll(final Class<T> entityType, Observer<List<T>> observer) {
        Observable<List<T>> observable = Observable.fromCallable(new Callable<List<T>>() {

            @Override
            public List<T> call() {
                try {
                    return mDbManager.findAll(entityType);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subscription;

    }

    /**
     * 该方法返回第一个DbModel对象
     * DbModel本质就是一个key为当前表的字段，value为对应字段的值的一个HashMap.
     *
     * @param sqlInfo 举例：new SqlInfo("select * from person")
     *                sqlInfo对象的创建的构造参数只需要传入一个sql语句即可
     * @return
     */


    public Subscription findDbModelFirst(final SqlInfo sqlInfo, Observer<DbModel> observer) {
        Observable<DbModel> observable = Observable.fromCallable(new Callable<DbModel>() {

            @Override
            public DbModel call() {
                try {
                    return mDbManager.findDbModelFirst(sqlInfo);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subscription;
    }

    /**
     * 该方法返回指定表中第一个DbModel对象
     *
     * @param tableName
     * @return
     */
    public Subscription findDbModelFirst(final String tableName, Observer<DbModel> observer) {
        Observable<DbModel> observable = Observable.fromCallable(new Callable<DbModel>() {

            @Override
            public DbModel call() {
                try {
                    return mDbManager.findDbModelFirst(new SqlInfo("select * from " + tableName));
                } catch (DbException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subscription;
    }

    /**
     * 该方法的用途就是返回满足sqlInfo信息的所有数据的字段的一个集合
     *
     * @param sqlInfo
     * @return
     */

    public Subscription findDbModelAll(final SqlInfo sqlInfo, Observer<List<DbModel>> observer) {

        Observable<List<DbModel>> observable = Observable.fromCallable(new Callable<List<DbModel>>() {

            @Override
            public List<DbModel> call() {
                try {
                    return mDbManager.findDbModelAll(sqlInfo);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subscription;
    }

    /**
     * 该方法主要是用来进行一些特定条件的查找.Selector<T>类还有很多限定条件查找，
     * 可以通过本类获取DbManager对象，自行实现
     *
     * @param entityType
     * @param whereBuilder
     * @param <T>
     * @return
     */

    public <T extends BaseDb> Subscription selectorAll(final Class<T> entityType, final WhereBuilder whereBuilder, Observer<List<T>> observer) {
        Observable<List<T>> observable = Observable.fromCallable(new Callable<List<T>>() {

            @Override
            public List<T> call() {
                try {
                    return mDbManager.selector(entityType).where(whereBuilder).findAll();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subscription;


    }
    ///////////// update

    /**
     * 更新实体所对应的表中的对应实体的相关属性
     *
     * @param entity
     * @param updateColumnNames
     */


    public void update(final Object entity, final String... updateColumnNames) {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.update(entity, updateColumnNames);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 更新实体所对应的表中，满足限定的条件的所有实体的指定的属性
     *
     * @param entityType     实体类型
     * @param whereBuilder   限定条件
     * @param nameValuePairs 键值对形式的对象，用来指定实体中的对应属性(key)和值(value)
     * @return 返回修改的个数
     */


    public <T extends BaseDb> Subscription update(final Class<T> entityType, Observer<Integer> observer, final WhereBuilder whereBuilder, final KeyValue... nameValuePairs) {
        Observable<Integer> observable = Observable.fromCallable(new Callable<Integer>() {

            @Override
            public Integer call() {
                try {
                    return mDbManager.update(entityType, whereBuilder, nameValuePairs);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subscription;
    }


    ///////////// db

    /**
     * 删除库
     *
     * @throws DbException
     */

    public void dropDb() {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    mDbManager.dropDb();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 关闭数据库,
     * xUtils对同一个库的链接是单实例的, 一般不需要关闭它.
     */

    public void close() {
        try {
            mDbManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

