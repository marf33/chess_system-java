package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	//Importar tabuleiro
	private Board board;
	
	//Criar jogo
	public ChessMatch() {
		//Tamanho do tabuleiro
		board = new Board(8, 8);
		//Peças no tabuleiro
		initialSetup();
	}
	
	//Retorna uma matriz com peças de xadrez corresponente a uma partida
	public ChessPiece[][] getPieces() {
		//Matriz de peças de xadrez
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColums()];
		//Percorre a matriz do tabuleiro e coloca as posições do tabuleiro
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColums(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		//Coverter posição de Xadrez para uma posição de matriz
		Position position = sourcePosition.toPosition();
		//Validar posição de origem
		validateSourcePosition(position);
		//Retornar os movimentos possiveis da peça dessa posição
		return board.piece(position).possibleMoves();
	}
	
	//Executa o movimento de uma peça
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		//Posição de origem
		Position source = sourcePosition.toPosition();
		//Posição de destino
		Position target = targetPosition.toPosition();
		//Validação da posição de origem
		validateSourcePosition(source);
		//Validação da posição de destino
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece) capturedPiece;
	}
	
	//Fazer movimento da peça
	private Piece makeMove(Position source, Position target) {
		//remover a peça da posição de origem
		Piece p = board.removePiece(source);
		//remover a possivel peça que esteja na posição de destino e vai ser a peça capturada
		Piece capturedPiece = board.removePiece(target);
		//Colocar a posição que estava na origem na posição de destino
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	//Validação da posição de origem
	private void validateSourcePosition(Position position) {
		//Se não existir um apeça nesta posição
		if (!board.thereIsApiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		
		//Verifcar se existe movimentos possiveis para a peça, se não existir movimentos posssiveis para as peças tambem nao se pode utlizar essa peça como origem
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	//Validação da posição de destino
	private void validateTargetPosition(Position source, Position target) {
		//Se para a peça de origem, a posição de destino não é um movimento possível, não é possível mexer para o destino
		if (!board.piece(source).possibleMoves(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	//Colocar nova peça com as posições certas a1,a2...em vez de (0,0),(0,1), etc...
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	//Colocar peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
