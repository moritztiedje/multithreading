package multithreadingexample;

class QuersummenRegelFailedException extends RuntimeException{
    QuersummenRegelFailedException(int number){
        super("ICH WUSSTE ES!!!! DIE REGEL GILT NICHT FUER: " + number);
    }
}
