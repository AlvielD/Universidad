#include <iostream>

using namespace std;

int main()
{
    #define changdol 1.15
    #define changlibr 0.88
    float dolares, libras, euros;
    cout << "Inserte los euros que desea convertir: "; cin >> euros;
    dolares = euros * changdol, libras = euros * changlibr;
    cout << "Dolares: " << dolares << "\n" << "Libras: " << libras;
    return 0;
}
