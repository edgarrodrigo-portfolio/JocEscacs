# Joc d’Escacs per consola (Java)

Projecte d’escacs per consola on dos jugadors poden crear una partida, alternar torns (blanques/negres) i fer moviments en format algebraic, per exemple: `e2 e4`.

El programa:
- Mostra menús (inici, menú principal, menú de torn).
- Imprimeix el tauler amb coordenades.
- Valida el format del moviment i comprova si el moviment és legal segons la peça.
- Permet captura d’una peça enemiga amb confirmació (menú de captura).
- Finalitza la partida quan desapareix un rei del tauler.

Inclou proves automatitzades amb JUnit per validar moviments del **Peó** i del **Cavall**.

---

## Com executar el joc

Des de l’arrel del projecte (on hi ha `src/`):

```bash
javac -d . src/JocEscacs.java
java src.JocEscacs
