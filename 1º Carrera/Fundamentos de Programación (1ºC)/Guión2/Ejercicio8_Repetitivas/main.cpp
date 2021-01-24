#include <iostream>

using namespace std;

class Factorial
{
    int n;

public:

    void informacion();
    long factorial();

};

void Factorial::informacion()
{
    do
    {
        cout << "Introduzca numero del 1 al 20: ";
        cin >> n;

    }while(n<1 || n>20);

}

long Factorial::factorial()
{
    int i;
    long m;
    m = 1;

    for(i=1;i<=n;i++)
        m = m*i;

    return m;
}

int main()
{
    Factorial F;
    long m;

    F.informacion();
    m = F.factorial();

    cout << "El factorial deseado es " << m;

    return 0;
}
