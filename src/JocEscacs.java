package src;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class JocEscacs {

    
    /*CODI NO A ESTAT REESTRUCTURAT AMB IA, JO HO HE ORGANITZAT, COM A MI M'HA
    SEMBLAT I COM MÉS CLAR HO HE VIST TENINT EN COMPTE LES NORMES DE CLEAN CODE*/
    
    
    
    public final int boardSize = 8;
    public final char emptyCell = ' ';

    Scanner enterValue = new Scanner(System.in);

    String player1;
    String player2;

    int gameOption = 0;

    boolean turnWhite = true;
    boolean gameRunning = false;

    boolean isFinish = false;

    char[][] board;

    public JocEscacs() {
        resetBoard();
    }

    private void resetBoard() {
        // Estat inicial del tauler
        board = new char[][]{
                {'t','c','a','q','k','a','c','t'}, // negres
                {'p','p','p','p','p','p','p','p'},
                {' ',' ',' ',' ',' ',' ',' ',' '},
                {' ',' ',' ',' ',' ',' ',' ',' '},
                {' ',' ',' ',' ',' ',' ',' ',' '},
                {' ',' ',' ',' ',' ',' ',' ',' '},
                {'P','P','P','P','P','P','P','P'}, // blanques
                {'T','C','A','Q','K','A','C','T'}
        };
    }

    public static void main(String[] args) {
        JocEscacs game = new JocEscacs();
        game.startGame();
    }

    public void startGame() {
        showOnInit();
        gameConfig();
    }

    public void showOnInit() {
        System.out.println("||||||||||||||||||||||||||||||||||||||\n||||| BENVINGUT AL JOC D'ESCACS! |||||\n||||||||||||||||||||||||||||||||||||||\n\n\n");
    }

    public void gameConfig() {
        while (true) {
            System.out.println("---------------------\n||| INICI |||\n---------------------\n");
            System.out.println("1) Crear Partida\n2) Exit\n");
            System.out.print("--> Selecciona una opcio (1, 2): ");

            gameOption = intValidation();

            if (gameOption == 1) {
                whilePlaying();
            } else if (gameOption == 2) {
                System.exit(0);
            } else {
                System.out.println("|ERROR| --> Nombre fora dels rangs\n|TIP| --> Introdueix un valor entre 1 i 2.\n");
            }
        }
    }

    public void whilePlaying() {
        askPlayers();
        callMenu();
    }

    public void callMenu() {
        while (true) {
            System.out.println("---------------------\n||| MENU |||\n---------------------\n");
            System.out.println("1) Iniciar Partida\n2) Tornar al Inici\n3) Exit\n");
            System.out.print("--> Selecciona una opcio (1, 2, 3): ");

            gameOption = intValidation();

            if (gameOption == 1) {
                System.out.println("Iniciant Partida...\n");

                resetBoard();

                printTable();

                gameRunning = true;
                turnWhite = true;
                isFinish = false;

                playTurns();
            } else if (gameOption == 2) {
                return;
            } else if (gameOption == 3) {
                System.exit(0);
            } else {
                System.out.println("|ERROR| --> Nombre fora dels rangs\n|TIP| --> Introdueix un valor entre 1 i 3.\n");
            }
        }
    }

    public void askPlayers() {
        enterValue.nextLine();

        try {
            System.out.print("|JUGADOR 1| --> Introdueix el nom del Jugador 1 (Blanques): ");
            player1 = enterValue.nextLine();

            System.out.print("|JUGADOR 2| --> Introdueix el nom del Jugador 2 (Negres): ");
            player2 = enterValue.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("\nEntrada finalitzada. Sortint...");
            System.exit(0);
        }
    }

    public int intValidation() {
        while (true) {
            try {
                return enterValue.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("|ERROR| --> Valor introduit no es correcte.\n|TIP| --> Introdueix un valor numeric.");
                enterValue.nextLine();
                System.out.print("--> Torna a introduir: ");
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("\nEntrada finalitzada. Sortint...");
                System.exit(0);
            }
        }
    }

    public void printTable() {
        System.out.println("     a  b  c  d  e  f  g  h");
        for (int row = 0; row < boardSize; row++) {
            System.out.print(" " + (boardSize - row) + "  ");
            for (int col = 0; col < boardSize; col++) {
                char piece = board[row][col];
                if (piece == emptyCell) System.out.print("[ ]");
                else System.out.print("[" + piece + "]");
            }
            System.out.println(" " + (boardSize - row));
        }
        System.out.println("     a  b  c  d  e  f  g  h\n");
    }

    public void playTurns() {
        while (gameRunning) {
            if (turnWhite) {
                System.out.println("|TORN BLANC| --> " + player1 + " (Blanques)");
            } else {
                System.out.println("|TORN NEGRE| --> " + player2 + " (Negres)");
            }
            turnMenu();
        }
    }

    public void turnMenu() {
        while (true) {
            System.out.println("1) Fer Moviment\n2) Tornar al Menu Principal\n3) Exit");
            System.out.print("--> ");

            gameOption = intValidation();

            if (gameOption == 1) {
                movement();
                return;
            } else if (gameOption == 2) {
                gameRunning = false;
                return;
            } else if (gameOption == 3) {
                System.exit(0);
            } else {
                System.out.println("|ERROR| --> Introdueix un valor entre 1 i 3.");
            }
        }
    }

    public void movement() {

        enterValue.nextLine();

        while (!isFinish) {
            System.out.print("Introdueix el moviment (ex: e2 e4) o 'cancel' per tornar: ");

            String line;
            try {
                line = enterValue.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("\nEntrada finalitzada. Sortint...");
                System.exit(0);
                return;
            }

            if (line.equalsIgnoreCase("cancel")) {
                System.out.println("Moviment cancel-lat.\n");
                return;
            }

            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                System.out.println("|ERROR| --> Format incorrecte.");
                System.out.println("|TIP| --> Exemple correcte: e2 e4 (separat per espai)\n");
                continue;
            }

            int[] fromPos = parsePosition(parts[0]);
            int[] toPos = parsePosition(parts[1]);

            if (fromPos == null || toPos == null) {
                System.out.println("|ERROR| --> Posicio incorrecta.");
                System.out.println("|TIP| --> Has d'escriure una lletra (a-h) i un numero (1-8). Exemple: e2 e4\n");
                continue;
            }

            boolean moved = makeMove(fromPos[0], fromPos[1], toPos[0], toPos[1]);
            if (moved) {
                printTable();
                checkWinner(); 
                return;
            }

            System.out.println();
        }
    }

    public int[] parsePosition(String pos) {

        if (pos == null) return null;
        pos = pos.trim();

        if (pos.length() != 2) return null;

        char file = Character.toLowerCase(pos.charAt(0));
        char rank = pos.charAt(1);

        if (file < 'a' || file > 'h') return null;
        if (rank < '1' || rank > '8') return null;

        int col = file - 'a';
        int row = boardSize - (rank - '0');

        return new int[]{row, col};
    }

    public boolean validMove(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }

    public boolean makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (!validMove(fromRow, fromCol) || !validMove(toRow, toCol)) {
            System.out.println("|ERROR| --> Coordenades fora del tauler.");
            return false;
        }

        if (fromRow == toRow && fromCol == toCol) {
            System.out.println("|ERROR| --> L'origen i el desti son la mateixa casella.");
            return false;
        }

        char origin = board[fromRow][fromCol];
        char destination = board[toRow][toCol];

        if (origin == emptyCell) {
            System.out.println("|ERROR| --> No hi ha cap peca a la posicio d'origen.");
            return false;
        }

        if (turnWhite && !Character.isUpperCase(origin)) {
            System.out.println("|ERROR| --> Es el torn de les BLANQUES!");
            return false;
        }
        if (!turnWhite && !Character.isLowerCase(origin)) {
            System.out.println("|ERROR| --> Es el torn de les NEGRES!");
            return false;
        }

        if (destination != emptyCell && isWhiteOrBlack(origin, destination)) {
            System.out.println("|ERROR| --> Ja tens una peca teva al desti!");
            return false;
        }

        if (!isLegalMove(fromRow, fromCol, toRow, toCol, origin, destination)) {
            System.out.println("|ERROR| --> Moviment illegal per a aquesta peca.");
            System.out.println("|TIP| --> Revisa com mou aquesta peca i si hi ha peces pel mig.");
            return false;
        }

        if (destination != emptyCell && !isWhiteOrBlack(origin, destination)) {
            return killMenu(fromRow, fromCol, toRow, toCol);
        }

        movement(fromRow, fromCol, toRow, toCol);
        turnWhite = !turnWhite;
        return true;
    }

    public void movement(int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = emptyCell;
    }

    public boolean isWhiteOrBlack(char a, char b) {
        if (a == emptyCell || b == emptyCell) return false;
        return (Character.isUpperCase(a) && Character.isUpperCase(b)) ||
                (Character.isLowerCase(a) && Character.isLowerCase(b));
    }

    public boolean killMenu(int fromRow, int fromCol, int toRow, int toCol) {
        while (true) {
            System.out.println("Hi ha una peca enemiga al desti!");
            System.out.println("1) Capturar peca\n2) Cancel-lar moviment");
            System.out.print("--> ");

            int option = intValidation();

            if (option == 1) {
                movement(fromRow, fromCol, toRow, toCol);
                System.out.println("Peca capturada.");
                turnWhite = !turnWhite;
                return true;
            } else if (option == 2) {
                System.out.println("Moviment cancel-lat.");
                return false;
            } else {
                System.out.println("|ERROR| --> Introdueix un valor entre 1 i 2.\n");
            }
        }
    }

    public boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol, char origin, char destination) {
        char pieceType = Character.toLowerCase(origin);
        int deltaRow = toRow - fromRow;
        int deltaCol = toCol - fromCol;

        switch (pieceType) {
            case 'p':
                return pawnMove(fromRow, fromCol, toRow, toCol, origin, destination);
            case 't':
                return rookMove(fromRow, fromCol, toRow, toCol) && pathClear(fromRow, fromCol, toRow, toCol);
            case 'c':
                return horseMove(deltaRow, deltaCol);
            case 'a':
                return bishopMove(deltaRow, deltaCol) && pathClear(fromRow, fromCol, toRow, toCol);
            case 'q':
                return queenMove(deltaRow, deltaCol) && pathClear(fromRow, fromCol, toRow, toCol);
            case 'k':
                return kingMove(deltaRow, deltaCol);
            default:
                System.out.println("|ERROR| --> Tipus de peca desconegut: " + origin);
                return false;
        }
    }

    public boolean pawnMove(int fromRow, int fromCol, int toRow, int toCol, char origin, char destination) {
        boolean isWhitePiece = Character.isUpperCase(origin);
        int direction = isWhitePiece ? -1 : 1;
        int startRow = isWhitePiece ? 6 : 1;

        int deltaRow = toRow - fromRow;
        int deltaCol = toCol - fromCol;

        if (deltaCol == 0) {
            if (destination != emptyCell) return false;

            if (deltaRow == direction) return true;

            if (fromRow == startRow && deltaRow == 2 * direction) {
                int middleRow = fromRow + direction;
                return board[middleRow][fromCol] == emptyCell;
            }
            return false;
        }

        if (Math.abs(deltaCol) == 1 && deltaRow == direction) {
            return destination != emptyCell && !isWhiteOrBlack(origin, destination);
        }

        return false;
    }

    public boolean rookMove(int fromRow, int fromCol, int toRow, int toCol) {
        return (fromRow == toRow && fromCol != toCol) || (fromCol == toCol && fromRow != toRow);
    }

    public boolean bishopMove(int deltaRow, int deltaCol) {
        return Math.abs(deltaRow) == Math.abs(deltaCol) && deltaRow != 0;
    }

    public boolean queenMove(int deltaRow, int deltaCol) {
        boolean likeRook = (deltaRow == 0 && deltaCol != 0) || (deltaCol == 0 && deltaRow != 0);
        boolean likeBishop = Math.abs(deltaRow) == Math.abs(deltaCol) && deltaRow != 0;
        return likeRook || likeBishop;
    }

    public boolean horseMove(int deltaRow, int deltaCol) {
        int absRow = Math.abs(deltaRow);
        int absCol = Math.abs(deltaCol);
        return (absRow == 2 && absCol == 1) || (absRow == 1 && absCol == 2);
    }

    public boolean kingMove(int deltaRow, int deltaCol) {
        return Math.abs(deltaRow) <= 1 && Math.abs(deltaCol) <= 1 && !(deltaRow == 0 && deltaCol == 0);
    }

    public boolean pathClear(int fromRow, int fromCol, int toRow, int toCol) {

        int stepRow = Integer.compare(toRow, fromRow);
        int stepCol = Integer.compare(toCol, fromCol);

        int currentRow = fromRow + stepRow;
        int currentCol = fromCol + stepCol;

        while (currentRow != toRow || currentCol != toCol) {
            if (board[currentRow][currentCol] != emptyCell) {
                return false;
            }
            currentRow += stepRow;
            currentCol += stepCol;
        }
        return true;
    }

    public void checkWinner() {
        boolean whiteKingAlive = onBoard('K');
        boolean blackKingAlive = onBoard('k');

        if (whiteKingAlive && blackKingAlive) return;

        isFinish = true;
        gameRunning = false;

        if (!blackKingAlive && whiteKingAlive) {
            System.out.println("\n|||| PARTIDA FINALITZADA ||||");
            System.out.println("Guanyador: " + player1 + " (Blanques)\n");
        } else if (!whiteKingAlive && blackKingAlive) {
            System.out.println("\n|||| PARTIDA FINALITZADA ||||");
            System.out.println("Guanyador: " + player2 + " (Negres)\n");
        } else {
            System.out.println("\n|||| PARTIDA FINALITZADA ||||");
            System.out.println("Resultat: empat (no hi ha reis al tauler)\n");
        }
    }

    public boolean onBoard(char pieceToFind) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] == pieceToFind) return true;
            }
        }
        return false;
    }
}
