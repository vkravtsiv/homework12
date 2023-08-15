package homework12;

/*
Напишіть програму, яка кожну секунду відображає на екрані дані про час,
що минув від моменту запуску програми. Другий потік цієї ж програми
кожні 5 секунд виводить повідомлення Минуло 5 секунд.
 */
public class ElapsedTime {
    public static void main(String[] args) {
        int i=0;
        Thread thread=new Thread(()->{
            while (true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread " + Thread.currentThread().getName() + " Ellapsed 5 second.");
                System.out.println();
            }
        } );
        thread.start();
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread "+Thread.currentThread().getName()+" Elapsed Time "+ ++i+" second");

        }
    }
}
