function boolean test5 (string name, int age, boolean isClient){
    if (!isClient) isClient = true;
    var string  u = 'underage';
    while (age < 18){
        print(isClient);
        print(u);
        age = age + 1;
    }
    return isClient;
}