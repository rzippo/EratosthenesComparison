#include <iostream>
#include <vector>

using namespace std;

int numberToPosition(int i)
{
	return i - 3;
}

int positionToNumber(int i)
{
	return i + 3;
}

int nextPrime(int* bools, int size, int prime)
{
	for (int i = numberToPosition(prime + 1); i < size; ++i)
	{
		if (bools[i])
			return positionToNumber(i);
	}

	return 0;
}

void deleteMultiples(int* numbers, bool* isPrime, int n, int currentPrime)
{
	for (int i = numberToPosition(currentPrime + 1); i < n; ++i)
	{
		if (numbers[i] % currentPrime == 0)
		{
			isPrime[i] = false;
		}
	}

	vector<int> prova(numbers, numbers+n);

	for (int i = 0; i < prova.size(); ++i)
	{
		prova[i] += 1;
	}
}
int main(int argc, char* argv[])
{
	if (argc < 2)
	{
		cout << ("usage ./sieve max_number_to_check") << endl;
		exit(-1);
	}
	
	int n = atoi(argv[1]);

	int* numbers = new int[n];
	bool* isPrime = new bool[n];

	memset(isPrime, true, n*sizeof(bool));

	for (int i = 0; i < n; ++i)
		numbers[i] = positionToNumber(i);

	int currentPrime = 2;

	do
	{
		deleteMultiples(numbers, isPrime, n, currentPrime);

		currentPrime = nextPrime(numbers, n, currentPrime);
	} while (currentPrime > 0);

	cout << "1 2 ";
	for (int i = 0; i < n; ++i)
	{
		if (isPrime[i])
			cout << numbers[i] << " ";
	}

	cout << endl;
}