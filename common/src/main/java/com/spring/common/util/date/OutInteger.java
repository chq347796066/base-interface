package com.spring.common.util.date;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:
*/
public class OutInteger {
    public OutInteger(int value){
        mValue = 0;
        mValue = value;
    }
    public void setValue(int value){
        mValue = value;
    }

    public int getValue(){
        return mValue;
    }

    public int inc(){
        return ++mValue;
    }

    public int inc(int i){
        return mValue += i;
    }

    public int desc() {
        return --mValue;
    }

    public int desc(int i) {
        return mValue -= i;
    }
    protected int mValue;

}
