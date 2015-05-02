public static class Sign implements Comparator<int>{
    public static int compare(int i1, int i2){
        if(i1<i2){
            return -1;
        }
        else if(i1==i2){
            return 0;
        }
        else{
            return 1;
        }
    }
}