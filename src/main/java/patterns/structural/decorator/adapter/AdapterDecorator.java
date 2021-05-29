package patterns.structural.decorator.adapter;


class MyStringBuilder {
    private StringBuilder sb;

    public MyStringBuilder(){
        sb = new StringBuilder();
    }

    public MyStringBuilder(String str) {
        this.sb = new StringBuilder(str);
    }

    //These methods adapt the stringbuilder to the string (they are String methods)
    public MyStringBuilder concat(String str){
        return new MyStringBuilder(sb.toString().concat(str));
    }

    public MyStringBuilder appendLine(String str){
        sb.append(str).append(System.lineSeparator());
        return this;
    }

    // delegate methods

    //These methods are not fluent, unfortunately
    public StringBuilder append(Object obj) {
        return sb.append(obj);
    }

    //Whhat you need is this (but Intellij wont cut it
    //public MyStringBuilder append(Object obj){
        //sb.append(obj);
        //return this;
    //}

    //public StringBuilder append(String str) {
        //return sb.append(str);
    //}
    public MyStringBuilder append(String str) {
        sb.append(str);
        return this;
    }

    public StringBuilder append(StringBuffer sb) {
        return this.sb.append(sb);
    }

    public StringBuilder append(CharSequence s) {
        return sb.append(s);
    }

    public StringBuilder append(CharSequence s, int start, int end) {
        return sb.append(s, start, end);
    }

    public StringBuilder append(char[] str) {
        return sb.append(str);
    }

    public StringBuilder append(char[] str, int offset, int len) {
        return sb.append(str, offset, len);
    }

    public StringBuilder append(boolean b) {
        return sb.append(b);
    }

    public StringBuilder append(char c) {
        return sb.append(c);
    }

    public StringBuilder append(int i) {
        return sb.append(i);
    }

    public StringBuilder append(long lng) {
        return sb.append(lng);
    }

    public StringBuilder append(float f) {
        return sb.append(f);
    }

    public StringBuilder append(double d) {
        return sb.append(d);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}


class Demo{
    public static void main(String[] args) {
        MyStringBuilder msb = new MyStringBuilder();
        msb.append("hello").appendLine(" world");
        System.out.println(msb.concat("and this too"));
    }
}