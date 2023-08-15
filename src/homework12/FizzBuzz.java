package homework12;

import java.util.function.IntConsumer;
/*
Напишіть програму, що виводить в консоль рядок, що складається з чисел від 1 до n,
але з заміною деяких значень:

якщо число ділиться на 3 - вивести fizz
якщо число ділиться на 5 - вивести buzz
якщо число ділиться на 3 і на 5 одночасно - вивести fizzbuzz
Наприклад, для n = 15, очікується такий результат:
1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz.

Програма повинна бути багатопотоковою, і працювати з 4 потоками:

Потік A викликає fizz(), щоб перевірити, чи ділиться число на 3,
і якщо так - додати в чергу на виведення для потоку D рядок fizz.
Потік B викликає buzz(), щоб перевірити, чи ділиться число на 5,
і якщо так - додати в чергу на виведення для потоку D рядок buzz.
Потік C викликає fizzbuzz(), щоб перевірити, чи ділиться число на 3 та 5 одночасно,
і якщо так - додати в чергу на виведення для потоку D рядок fizzbuzz.
Потік D викликає метод number(), щоб вивести наступне число з черги,
якщо є таке число для виведення.
 */
public class FizzBuzz {
    private int n;
    private int num = 1;

    public FizzBuzz(int n) {
        this.n = n;
    }

    public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (num <= n) {
            if (num % 15 == 0) {
                printFizzBuzz.run();
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void fizz(Runnable printFizz) throws InterruptedException {
        while (num <= n) {
            if (num % 3 == 0 && num % 5 != 0) {
                printFizz.run();
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
        while (num <= n) {
            if (num % 3 != 0 && num % 5 == 0) {
                printBuzz.run();
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void number(IntConsumer printNumber) throws InterruptedException {
        while (num <= n) {
            if (num % 3 != 0 && num % 5 != 0) {
                printNumber.accept(num);
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FizzBuzz fizzBuzz = new FizzBuzz(20);
        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> System.out.print("fizz, "));
            } catch (Exception e) {
            }
        });
        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> System.out.print("buzz, "));
            } catch (Exception e) {
            }
        });
        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> System.out.print("fizzbuzz, "));
            } catch (Exception e) {
            }
        });
        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number(i -> System.out.print(i+", "));
            } catch (Exception e) {
            }
            System.out.println("\b\b.");
        });
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        threadA.join();
        threadB.join();
        threadC.join();
        threadD.join();
    }
}
