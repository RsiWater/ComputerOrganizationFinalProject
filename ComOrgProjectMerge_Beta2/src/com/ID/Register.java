package com.ID;

public class Register
{
    private int value;
    private boolean isZero;
    public Register()
    {
        value=1;
        isZero=false;
    }
    public void setZero() {
    	value=0;
    	isZero=true;
    }
    public boolean isZero() { return isZero; }
    public void setValue(int value) { this.value = value; }
    public int getValue() { return this.value; }
    public void Reset() { this.value = 0; }
}