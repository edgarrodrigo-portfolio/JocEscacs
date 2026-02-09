package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PawnMoveTest {

    @Test
    @DisplayName("Peó: moviment d'1 casella endavant (vàlid si està buida)")
    void pawn_oneStepForward_validIfEmpty() {
        JocEscacs game = new JocEscacs();

        // Peó blanc a e2: (6,4) -> e3: (5,4)
        boolean result = game.pawnMove(6, 4, 5, 4, 'P', game.emptyCell);

        assertTrue(result);
    }

    @Test
    @DisplayName("Peó: moviment de 2 caselles des de la posició inicial (vàlid si camí lliure)")
    void pawn_twoStepsFromStart_validIfPathClear() {
        JocEscacs game = new JocEscacs();

        // Peó blanc a e2: (6,4) -> e4: (4,4) (casella intermèdia buida)
        boolean result = game.pawnMove(6, 4, 4, 4, 'P', game.emptyCell);

        assertTrue(result);
    }

    @Test
    @DisplayName("Peó: intent d'avançar si hi ha peça davant (invàlid)")
    void pawn_forwardBlocked_invalid() {
        JocEscacs game = new JocEscacs();

        // Bloquegem e3 (5,4)
        game.board[5][4] = 'p';

        boolean result = game.pawnMove(6, 4, 5, 4, 'P', game.board[5][4]);
        assertFalse(result);
    }

    @Test
    @DisplayName("Peó: captura diagonal sobre peça rival (vàlid)")
    void pawn_diagonalCapture_enemy_valid() {
        JocEscacs game = new JocEscacs();

        // Posem enemic a f3 (5,5)
        game.board[5][5] = 'p';

        boolean result = game.pawnMove(6, 4, 5, 5, 'P', game.board[5][5]);
        assertTrue(result);
    }

    @Test
    @DisplayName("Peó: intent de captura diagonal si no hi ha peça rival (invàlid)")
    void pawn_diagonalCapture_noEnemy_invalid() {
        JocEscacs game = new JocEscacs();

        // f3 buit
        boolean result = game.pawnMove(6, 4, 5, 5, 'P', game.emptyCell);
        assertFalse(result);
    }

    @Test
    @DisplayName("Peó: intent de moure enrere (invàlid)")
    void pawn_moveBackwards_invalid() {
        JocEscacs game = new JocEscacs();

        // e2 -> e1 (6,4) -> (7,4) és enrere per un peó blanc
        boolean result = game.pawnMove(6, 4, 7, 4, 'P', game.emptyCell);
        assertFalse(result);
    }
}
