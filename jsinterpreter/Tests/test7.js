function int test1(int i, boolean ok){
    var int num;
    input(num);
    while(i < 4){
        num -= i;
        if (num < 8) ok = true;
    }
    var string hola = 'Hola caracola';
    print(hola);
    return hola;
}