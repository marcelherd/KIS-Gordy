class ColorState {

    int redLeft, greenLeft, blueLeft;
    int redRight, greenRight, blueRight;
    public ColorState(int redLeft, int greenLeft, int blueLeft, int redRight, int greenRight, int blueRight){
        this.redLeft = redLeft;
        this.greenLeft = greenLeft;
        this.blueLeft = blueLeft;
        this.redRight = redRight;
        this.greenRight = greenRight;
        this.blueRight = blueRight;
    }

    ColorState multiply(BigDecimal other){
        other = 1/(256*other);
        return new ColorState((int)(redLeft*other), (int)(greenLeft*other), (int)(blueLeft*other), (int)(redRight*other), (int)(greenRight*other), (int)(blueRight*other));
    }
}
