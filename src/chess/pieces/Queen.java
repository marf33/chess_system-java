package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possibleMoves() {
		// Todas as posições com o valor falso
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColums()];

		Position p = new Position(0, 0);

		// Acima da minha peça - a linha acima da peça do jogador e a mesma coluna
		p.setValues(position.getRow() - 1, position.getColumn());

		// Enquanto a posição p existir e não tiver uma peça nessa posição - ou seja
		// enquato a posição tiver livre
		while (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
			// posição livre marcado como true, ou seja, está livre
			mat[p.getRow()][p.getColumn()] = true;
			// Mover mais uma posição da peça para cima
			p.setRow(p.getRow() - 1);
		}

		// Verificar se é uma peça adversária e Marcar a peça adversária como true
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			// posição da peça adversária marcado como true
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Esquerda da minha peça - a mesma linha e a coluna à esquerda da peça do
		// jogador
		p.setValues(position.getRow(), position.getColumn() - 1);

		// Enquanto a posição p existir e não tiver uma peça nessa posição - ou seja
		// enquato a posição tiver livre
		while (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
			// posição livre marcado como true, ou seja, está livre
			mat[p.getRow()][p.getColumn()] = true;
			// Mover mais uma posição da peça para a esquerda
			p.setColumn(p.getColumn() - 1);
		}

		// Verificar se é uma peça adversária e Marcar a peça adversária como true
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			// posição da peça adversária marcado como true
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Direita da minha peça - a mesma linha e a coluna à direita da peça do jogador
		p.setValues(position.getRow(), position.getColumn() + 1);

		// Enquanto a posição p existir e não tiver uma peça nessa posição - ou seja
		// enquato a posição tiver livre
		while (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
			// posição livre marcado como true, ou seja, está livre
			mat[p.getRow()][p.getColumn()] = true;
			// Mover mais uma posição da peça para a direita
			p.setColumn(p.getColumn() + 1);
		}

		// Verificar se é uma peça adversária e Marcar a peça adversária como true
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			// posição da peça adversária marcado como true
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Abaixo da minha peça - a linha abaixo da peça do jogador e a mesma coluna
		p.setValues(position.getRow() + 1, position.getColumn());

		// Enquanto a posição p existir e não tiver uma peça nessa posição - ou seja
		// enquato a posição tiver livre
		while (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
			// posição livre marcado como true, ou seja, está livre
			mat[p.getRow()][p.getColumn()] = true;
			// Mover mais uma posição da peça para cima
			p.setRow(p.getRow() + 1);
		}

		// Verificar se é uma peça adversária e Marcar a peça adversária como true
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			// posição da peça adversária marcado como true
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Diagonal cima esquerda da minha peça
		p.setValues(position.getRow() - 1, position.getColumn() - 1);

		// Enquanto a posição p existir e não tiver uma peça nessa posição - ou seja
		// enquato a posição tiver livre
		while (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
			// posição livre marcado como true, ou seja, está livre
			mat[p.getRow()][p.getColumn()] = true;
			// Mover mais uma posição da peça para diagonal esquerda cima
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}

		// Verificar se é uma peça adversária e Marcar a peça adversária como true
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			// posição da peça adversária marcado como true
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Diagonal cima direita da minha peça
		p.setValues(position.getRow() - 1, position.getColumn() + 1);

		// Enquanto a posição p existir e não tiver uma peça nessa posição - ou seja
		// enquato a posição tiver livre
		while (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
			// posição livre marcado como true, ou seja, está livre
			mat[p.getRow()][p.getColumn()] = true;
			// Mover mais uma posição da peça para a diagonal cima direita
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}

		// Verificar se é uma peça adversária e Marcar a peça adversária como true
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			// posição da peça adversária marcado como true
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Diagonal baixo esquerda da minha peça
		p.setValues(position.getRow() + 1, position.getColumn() - 1);

		// Enquanto a posição p existir e não tiver uma peça nessa posição - ou seja
		// enquato a posição tiver livre
		while (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
			// posição livre marcado como true, ou seja, está livre
			mat[p.getRow()][p.getColumn()] = true;
			// Mover mais uma posição da peça para a diagonal baixo esquerda
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}

		// Verificar se é uma peça adversária e Marcar a peça adversária como true
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			// posição da peça adversária marcado como true
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Diagonal baixo direita da minha peça
		p.setValues(position.getRow() + 1, position.getColumn() + 1);

		// Enquanto a posição p existir e não tiver uma peça nessa posição - ou seja
		// enquato a posição tiver livre
		while (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
			// posição livre marcado como true, ou seja, está livre
			mat[p.getRow()][p.getColumn()] = true;
			// Mover mais uma posição da peça para a diagonal baixo direita
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}

		// Verificar se é uma peça adversária e Marcar a peça adversária como true
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			// posição da peça adversária marcado como true
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
}
