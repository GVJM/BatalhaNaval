package br.com.letscode.batalhanaval;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Board {

    //Array de navios, em cada célula teremos quatro espaços,
    // o primeiro para os navios do jogador, o segundo para navios do computador,
    //o terceiro para tiros do jogador e o quarto para tiros do computador.
    //Valor 1 significa que existe e 0 que não existe.
    private int[][][] boardArray = new int[10][10][4];

    private int playerScore;

    private int cpuScore;

    //Variável booleana utilizada para personalizar a saída(print) da função gameEnding;
    public Boolean playerIsWinner;

    //Lista de caracteres utilizados nas linhas da tabela
    final char[] linesLetters= {'A','B','C','D','E','F','G','H','I','J'};

    //Função que imprime no console o tabuleiro do jogador
    public void printBoard(){
        //O que é exibido durante o jogo
        if(playerScore<10 && cpuScore<10){
            System.out.println("\n\n\n\n---------------------------------------------");
            System.out.println("| N = Navio do jogador | * = Tiro certeiro  |");
            System.out.println("| - = Tiro na água                          |");
            System.out.println("| X = Tiro certeiro com navio posicionado   |");
            System.out.println("| n = Tiro na água com navio posicionado    |");
            System.out.println("---------------------------------------------");
            System.out.println("|                   JOGADOR                 |");
            System.out.println("---------------------------------------------");
            System.out.println("|   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |");
            //System.out.println("---------------------------------------------");
            for (int i = 0; i < boardArray.length; ++i) {
                for(int j = 0; j < boardArray[i].length; ++j) {

                    if(j==0){
                        System.out.print("| "+linesLetters[i]+" ");
                    }

                    //Caso não haja navio do jogador ou ele foi atingido
                    if(boardArray[i][j][0] == 0 || (boardArray[i][j][0] == 1 && boardArray[i][j][3] == 1)) {

                        //Caso não tenha tiro
                        if (boardArray[i][j][2] == 0) {
                            System.out.print("|   ");
                        }

                        //Caso tenha tiro do jogador
                        else {

                            //Tiro na água
                            if (boardArray[i][j][1] == 0) {
                                System.out.print("| - ");
                            }

                            //Tiro certeiro
                            else{
                                System.out.print("| * ");
                            }
                        }
                    }

                    //Caso haja
                    else{
                        //Caso não haja tiro do jogador
                        if(boardArray[i][j][2] == 0){
                            System.out.print("| N ");
                        }

                        //Caso haja tiro do jogador
                        else{
                            //Tiro na água
                            if(boardArray[i][j][1]==0){
                                System.out.print("| n ");
                            }

                            //Tiro certeiro
                            else{
                                System.out.print("| X ");
                            }
                        }
                    }
                }
                System.out.println("|");
                //System.out.println("---------------------------------------------");

            }
            System.out.println("---------------------------------------------");
        }

        //Print de fim de jogo mostrando o tabuleiro do jogador e o do computador
        else{

            System.out.println("\n\n\n\n\n\n\n-----------------------------------------------------------------------------------------------");
            System.out.println("|                   JOGADOR                 |||||||                COMPUTADOR                 |");
            System.out.println("-----------------------------------------------------------------------------------------------");
            System.out.println("|   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |||||||   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |");

            for (int i = 0; i < boardArray.length; ++i) {

                //Navios do Jogador
                for(int j = 0; j < boardArray[i].length; ++j) {
                    if(j==0){
                        System.out.print("| "+linesLetters[i]+" ");
                    }
                    if(boardArray[i][j][0]==1){
                        System.out.print("| N ");
                    }
                    else{
                        System.out.print("|   ");
                    }
                }
                System.out.print("|");

                //Navios do computador
                for(int j = 0; j < boardArray[i].length; ++j) {
                    if(j==0){
                        System.out.print("|||||| "+linesLetters[i]+" ");
                    }
                    if(boardArray[i][j][1]==1){
                        System.out.print("| N ");
                    }
                    else{
                        System.out.print("|   ");
                    }
                }
                System.out.println("|");
            }
            System.out.println("-----------------------------------------------------------------------------------------------");

        }

        System.out.println("\nPlacar:    [Jogador-"+playerScore+" | Computador-"+cpuScore+"]\n");
    }

    //Função para incluir novos barcos
    public boolean setBoat(int line, int column, boolean player){
        //Variável que é retornada como true se a operação foi um sucesso ou false caso não(caso aquele dado já tenha sido preenchido)
        boolean success = true;

        if(player){
            if(boardArray[line][column][0]==0){
                boardArray[line][column][0]=1;
            }
            else{ success = false; }
        }
        else{
            if(boardArray[line][column][1]==0){
                boardArray[line][column][1]=1;
            }
            else{ success = false; }
        }

        return success;
    }

    //Função para incluir novos tiros
    public boolean setShot(int line, int column, boolean player){
        //Variável que avisa se a operação foi um sucesso ou não(caso aquele dado já tenha sido preenchido)
        boolean success = true;

        if(player){
            if(boardArray[line][column][2]==0){
                boardArray[line][column][2]=1;
                if(boardArray[line][column][1]==1){
                    ++playerScore;
                }
            }
            else{ success = false; }
        }
        else{
            if(boardArray[line][column][3]==0){
                boardArray[line][column][3]=1;
                if(boardArray[line][column][0]==1){
                    ++cpuScore;
                }
            }
            else{ success = false; }
        }

        return success;
    }

    //Função para gerar 10 navios aleatórios
    public void randomShips(Boolean player){
        //Loop while para inserção dos navios nos indices [line][column] a partir de inteiros aleatórios,
        //com verificação de valores válidos.
        int count = 0;
        while(count<10){
            int line = ThreadLocalRandom.current().nextInt(0, 10);
            int column = ThreadLocalRandom.current().nextInt(0, 10);
            if(setBoat(line,column,player)){
                ++count;
            }
        }
    }

    //Função para inserção manual dos navios do jogador
    public void playerShips(){
        Scanner scanner = new Scanner(System.in);

        printBoard();
        System.out.print("Caso queira inserir seus navios manualmente digite SIM, caso contrário digite qualquer outro valor:");
        String insercaoManual = scanner.next().trim().toUpperCase();
        if(insercaoManual.equals("SIM")){
            //Loop para inserção dos barcos do jogador
            int count = 0;
            while(count<10){

                int lineIndex;
                int columnIndex;

                System.out.printf("Informe a linha do seu barco nº%d: ",(count+1));
                char letterInput = scanner.next().toUpperCase().charAt(0);

                Boolean invalidLine = true;

                for (int i = 0; i < linesLetters.length; i++) {
                    if( linesLetters[i] == letterInput ){
                        lineIndex=i;
                        invalidLine=false;
                        boolean invalidColumn = true;

                        while(invalidColumn){
                            int columnInput;
                            while(true){
                                try{
                                    System.out.print("Informe a coluna do seu tiro (use somente números de 0 à 9): ");
                                    columnInput = scanner.nextInt();
                                    break;

                                }catch (Exception e){
                                    System.out.println("Valor inválido.");
                                    scanner.next();
                                }
                            }
                            if(0<=columnInput && columnInput<=9){

                                columnIndex= columnInput;

                                //Realiza adição do valor. Caso a função retorne falso,
                                //este espaço já fora preenchido e não iremos contar esta ação.
                                if(setBoat(lineIndex,columnIndex,true)){
                                    invalidColumn=false;
                                    count++;

                                    //Imprime próxima board
                                    printBoard();
                                }
                                else{
                                    System.out.println("Coluna inválida.");
                                    break;
                                }
                            }
                            else{
                                System.out.println("Valor inválido, tente novamente.");
                            }
                        }
                    }
                }

                if(invalidLine){
                    System.out.println("Valor inválido, tente novamente.");
                }
            }

        }

        else {
            randomShips(true);
        }

    }

    //Função de tiros do cpu
    public void cpuShoots(){
        Boolean invalidShot = true;
        while(invalidShot){
            int line = ThreadLocalRandom.current().nextInt(0, 10);
            int column = ThreadLocalRandom.current().nextInt(0, 10);
            if(setShot(line,column,false)){
                invalidShot=false;
            }
        }
    }

    //Função de interação com jogador para tiros
    public void playerShoots(){
        Scanner scanner = new Scanner(System.in);

        Boolean validCoord = false;
        while(!validCoord) {

            int lineIndex;
            int columnIndex;

            System.out.print("Informe a linha do seu tiro (use somente caracteres de A à J): ");
            char letterInput = scanner.next().toUpperCase().charAt(0);

            Boolean invalidLine = true;

            for (int i = 0; i < linesLetters.length; i++) {
                if( linesLetters[i] == letterInput ){
                    lineIndex=i;
                    invalidLine=false;
                    boolean invalidColumn = true;

                    while(invalidColumn){
                        int columnInput;
                        while(true){
                            try{
                                System.out.print("Informe a coluna do seu tiro (use somente números de 0 à 9): ");
                                columnInput = scanner.nextInt();
                                break;

                            }catch (Exception e){
                                System.out.println("Valor inválido.");
                                scanner.next();
                            }
                        }

                        if(0<=columnInput && columnInput<=9){

                            columnIndex= columnInput;

                            //Realiza adição do valor. Caso a função retorne falso,
                            //este espaço já fora preenchido e não iremos contar esta ação.
                            if(setShot(lineIndex,columnIndex,true)){
                                invalidColumn=false;
                                validCoord=true;
                            }
                            else{
                                System.out.println("Coluna inválida.");
                                break;
                            }
                        }
                        else{
                            System.out.println("Valor inválido, tente novamente.");
                        }
                    }
                }
            }

            if(invalidLine){
                System.out.println("Valor inválido, tente novamente.");
            }
        }

    }

    //Função de exibição do fim do jogo, chamada quando um dos placares for 10
    public void gameEnding(){

        printBoard();

        if(playerIsWinner){
            System.out.println("\nParabéns!!! Você venceu.");
        }
        else{
            System.out.println("O computador venceu!");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Novo jogo (digite SIM ou NAO): ");

        String novoJogo = scanner.next().toUpperCase();
        if(novoJogo.equals("SIM")){
            gameInit();
        }
    }

    //Função de start do jogo
    public void gameInit(){


        //Preencher tabela com zeros
        for (int i = 0; i < boardArray.length; ++i) {
            for (int j = 0; j < boardArray[i].length; ++j) {
                boardArray[i][j][0]=0;
                boardArray[i][j][1]=0;
                boardArray[i][j][2]=0;
                boardArray[i][j][0]=0;
            }
        }

        playerScore=0;
        cpuScore=0;

        playerShips();
        
        randomShips(false);

        printBoard();

        while(cpuScore<10||playerScore<10){
            playerScore=10;

            playerShoots();
            if(playerScore>=10){
                playerIsWinner=true;
                break;
            }
            cpuShoots();
            if(cpuScore>=10){
                playerIsWinner=false;
                break;
            }
            printBoard();
        }

        gameEnding();
    }

    public static void main(String[] args) {
        Board jogo = new Board();
        jogo.gameInit();
    }
}
