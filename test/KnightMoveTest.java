package src;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KnightMoveTest {

    @Test
    @DisplayName("Cavall: moviment en L a una casella buida (vàlid)")
    void knight_lMoveToEmpty_valid() {
        JocEscacs game = new JocEscacs();

        // b1 (7,1) -> c3 (5,2): deltaRow = -2, deltaCol = +1
        boolean result = game.horseMove(5 - 7, 2 - 1);

        assertTrue(result);
    }

    @Test
    @DisplayName("Cavall: moviment en L capturant peça rival (vàlid)")
    void knight_lMoveCaptureEnemy_valid() {
        JocEscacs game = new JocEscacs();

        int fromRow = 7, fromCol = 1; // b1
        int toRow = 5, toCol = 2;     // c3

        game.board[fromRow][fromCol] = 'C';
        game.board[toRow][toCol] = 'p'; // enemic al destí

        boolean result = game.isLegalMove(fromRow, fromCol, toRow, toCol, 'C', 'p');
        assertTrue(result);
    }

    @Test
    @DisplayName("Cavall: intent de moure com un alfil/torre (invàlid)")
    void knight_moveLikeBishopOrRook_invalid() {
        JocEscacs game = new JocEscacs();

        // Moviment tipus torre/alfil (delta -1,0) no és L
        boolean result = game.horseMove(-1, 0);

        assertFalse(result);
    }

    @Test
    @DisplayName("Cavall: intent de sortir del tauler (invàlid)")
    void knight_outOfBoard_invalid() {
        JocEscacs game = new JocEscacs();

        game.turnWhite = true;
        game.board[7][1] = 'C';

        // Destí fora del tauler
        boolean result = game.makeMove(7, 1, -1, 2);
        assertFalse(result);
    }

    @Test
    @DisplayName("Cavall: intent de capturar peça pròpia (invàlid)")
    void knight_captureOwnPiece_invalid() {
        JocEscacs game = new JocEscacs();

        game.turnWhite = true;
        game.board[7][1] = 'C';  // cavall blanc
        game.board[5][2] = 'P';  // peça blanca al destí

        boolean result = game.makeMove(7, 1, 5, 2);
        assertFalse(result);
    }

    @Test
    @DisplayName("Cavall: verifica que salta peces (obstacles no afecten)")
    void knight_jumpsOverPieces_validEvenWithObstacles() {
        JocEscacs game = new JocEscacs();

        game.turnWhite = true;

        // Al tauler inicial hi ha peces al mig, però el cavall ha de poder saltar.
        // b1 (7,1) -> c3 (5,2)
        boolean result = game.makeMove(7, 1, 5, 2);

        assertTrue(result);
    }
}
