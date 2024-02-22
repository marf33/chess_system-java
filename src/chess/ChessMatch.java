package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	
	//Declaração de listas 
	//Lista de peças no tabuleiro
	private List<Piece> piecesOnTheBoard = new ArrayList<Piece>();
	//Lista de peças capturadas
	private List<Piece> capturedPieces = new ArrayList<Piece>();
	
	//Importar tabuleiro
	private Board board;
	
	//Para verificar se a partida está em posição de check ou não
	private boolean check;
	private boolean checkMate;
	
	private ChessPiece enPassantVulnerable;
	
	private ChessPiece promoted;
	
	//Criar jogo
	public ChessMatch() {
		//Tamanho do tabuleiro
		board = new Board(8, 8);
		//Turno do jogador
		turn = 1;
		//Cor do jogador inicial
		currentPlayer = Color.WHITE;
		//Peças no tabuleiro
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
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
		
		//Verificar se o movimento colocou o próprio jogador em check
		if (testCheck(currentPlayer)) {
			//Desfazer o movimento
			undoMove(source, target, capturedPiece);
			
			throw new ChessException("You can't put yourself in check");
		}
	
		//Peça que moveu foi a que foi colocada no destino
		ChessPiece movedPiece = (ChessPiece) board.piece(target);
		
		
		//Movimento especial -> Promotion
		//Para assegurar que estamos a fazre um novo teste
		promoted = null;
		
		//Se a peça movida foi um peão
		if (movedPiece instanceof Pawn) {
			
			//Se a peça movida for branca e a posião de destino for 0
			if (movedPiece.getColor() == Color.WHITE && target.getRow() == 0 || movedPiece.getColor() == Color.BLACK && target.getRow() == 7 ) {
				promoted = (ChessPiece) board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		//Verificar se o oponente se colocou em check
		if (check = testCheck(opponent(currentPlayer))) {
			check = true;
		}
		else {
			check =  false;
		}
		
		//Verificar se a jogada feita deixou o oponente em checkMate -> O jogo acaba
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		
		//Verificar se a peça movida foi um peão que moveu 2 casas, se for esse o caso, esse peão vai ser marcado como o peão que está vunlerável (Specialmove en passant)
		
		//Verificar se a peça que foi movida foi um peão e moveu 2 casas
		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		
		
		return (ChessPiece)capturedPiece;
	}
	
	
	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q") ) {
			throw new InvalidParameterException("Invalid type for promotion");
		}
		
		//Apanhar a posicão da peça promovida
		Position pos = promoted.getChessPosition().toPosition();
		//Remover a peça que estava na posição
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		//Instanciar a nova peça
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		//Colocar a nova peça na posição da peça promovida
		board.placePiece(newPiece, pos);
		//Adicionar na lista do tbuleiro a nova peça criada
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
		
	}
	
	//Instanciar uma nova peça consoante a letra escolhida
	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) {
			return new Bishop(board, color);
		}
		
		if (type.equals("N")) {
			return new Knight(board, color);
		}
		
		if (type.equals("Q")) {
			return new Queen(board, color);
		}
		
		
		return new Rook(board, color);
	}
	
	//Fazer movimento da peça
	private Piece makeMove(Position source, Position target) {
		//remover a peça da posição de origem
		ChessPiece p = (ChessPiece) board.removePiece(source);
		//Incrementar os movimentos
		p.increaseMoveCount();
		//remover a possivel peça que esteja na posição de destino e vai ser a peça capturada
		Piece capturedPiece = board.removePiece(target);
		//Colocar a posição que estava na origem na posição de destino
		board.placePiece(p, target);
		
		//Se a peça capturada for diferente de nulo captura-se uma peça
		if (capturedPiece != null) {
			//Remover a peça da lista de tabuleiro
			piecesOnTheBoard.remove(capturedPiece);
			//Adicionar peça na lista de peças capturadas
			capturedPieces.add(capturedPiece);
		}
		
		//Se moveu o rei 2 casas para a direita é uma torre "pequena"
		//Se moveu o rei 2 casas para a esquerda é uma torre "grande" 
		//Então temos que mover a torre manualmente (Specialmove castling kingside rook)
		//Verificar se a peça 'p' é uma instância de rei e a posição de destino  = posição de origem + 2 significa que o rei andou 2 casas para a direita
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			//Posição de origem da torre
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			//Posição de destino da torre
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			//Apanhar(Pegar/Retirar) a torre que estava nessa posição
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			//Colocar a torre na posição de destino dela
			board.placePiece(rook, targetT);
			//Incrementar a quantidade de movimentos
			rook.increaseMoveCount();
		}
		
		
		//Se moveu o rei 2 casas para a direita é uma torre "pequena"
		//Se moveu o rei 2 casas para a esquerda é uma torre "grande" 
		//Então temos que mover a torre manualmente (Specialmove castling queenside rook)
		//Verificar se a peça 'p' é uma instância de rei e a posição de destino  = posição de origem - 2 significa que o rei andou 2 casas para a esquerda
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			//Posição de origem da torre
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			//Posição de destino da torre
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			//Apanhar(Pegar/Retirar) a torre que estava nessa posição
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			//Colocar a torre na posição de destino dela
			board.placePiece(rook, targetT);
			//Incrementar a quantidade de movimentos
			rook.increaseMoveCount();
		}
		
		
		//Verificar se o movimento foi o enPassant 
		//Verificar se a peça p é instanciado pelo o peão
		if (p instanceof Pawn) {
			
			//O peão andou na diagonal e não capturou peça -> enPassant
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				
				//Se a peça que moveu for branca significa que a peça que está em baixo é a que vai ser capturada
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				}
				else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				
				//A peça capturada vai ser removido
				capturedPiece = board.removePiece(pawnPosition);
				//Adicionar na lista de peças capturadas
				capturedPieces.add(capturedPiece);
				//Remover as peças capturadas do tabuleiro
				piecesOnTheBoard.remove(capturedPiece);
				
			}
		}
		
		return capturedPiece;
	}
	
	//Desfazer o movimento da peça
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		//Remove a peça movida no destino
		ChessPiece p = (ChessPiece) board.removePiece(target);
		
		//Desfazer a contagem dos movimentos
		p.decreaseMoveCount();
		
		//Devolver à posição de origem
		board.placePiece(p, source);
		
		//Voltar a colocar a peça capturada na posição de destino
		if (capturedPiece != null) {
			//Colocar a peça na posição de destino
			board.placePiece(capturedPiece, target);
			
			//Retirar peça da lista de peças capturadas e colocar na lista de peças no tabuleiro
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
			
		}
		
		//Se moveu o rei 2 casas para a direita é uma torre "pequena"
				//Se moveu o rei 2 casas para a esquerda é uma torre "grande" 
				//Então temos que mover a torre manualmente (Specialmove castling kingside rook)
				//Verificar se a peça 'p' é uma instância de rei e a posição de destino  = posição de origem + 2 significa que o rei andou 2 casas para a direita
				if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
					//Posição de origem da torre
					Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
					//Posição de destino da torre
					Position targetT = new Position(source.getRow(), source.getColumn() + 1);
					//Apanhar(Pegar/Retirar) a torre que estava nessa posição
					ChessPiece rook = (ChessPiece) board.removePiece(targetT);
					//Colocar a torre na posição de destino dela
					board.placePiece(rook, sourceT);
					//Incrementar a quantidade de movimentos
					rook.decreaseMoveCount();
				}
				
				
				//Se moveu o rei 2 casas para a direita é uma torre "pequena"
				//Se moveu o rei 2 casas para a esquerda é uma torre "grande" 
				//Então temos que mover a torre manualmente (Specialmove castling queenside rook)
				//Verificar se a peça 'p' é uma instância de rei e a posição de destino  = posição de origem - 2 significa que o rei andou 2 casas para a esquerda
				if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
					//Posição de origem da torre
					Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
					//Posição de destino da torre
					Position targetT = new Position(source.getRow(), source.getColumn() - 1);
					//Apanhar(Pegar/Retirar) a torre que estava nessa posição
					ChessPiece rook = (ChessPiece) board.removePiece(targetT);
					//Colocar a torre na posição de destino dela
					board.placePiece(rook, sourceT);
					//Incrementar a quantidade de movimentos
					rook.decreaseMoveCount();
				}
		
				//Verificar se o movimento foi o enPassant 
				//Verificar se a peça p é instanciado pelo o peão
				if (p instanceof Pawn) {
					
					//O peão andou na diagonal e não capturou peça -> enPassant
					if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
						
						//Apanhar a peça que foi devolvida
						ChessPiece pawn = (ChessPiece) board.removePiece(target);
						Position pawnPosition;
						
						//Se a peça que moveu for branca significa que a peça que está em baixo é a que vai ser capturada
						if (p.getColor() == Color.WHITE) {
							pawnPosition = new Position(3, target.getColumn());
						}
						else {
							pawnPosition = new Position(4, target.getColumn());
						}
						
						//Colocar a peça no sitio certo
						board.placePiece(pawn, pawnPosition);
						
						//Adicionar na lista de peças capturadas
						capturedPieces.add(capturedPiece);
						//Remover as peças capturadas do tabuleiro
						piecesOnTheBoard.remove(capturedPiece);
						
					}
				}
	}
	
	//Validação da posição de origem
	private void validateSourcePosition(Position position) {
		//Se não existir um apeça nesta posição
		if (!board.thereIsApiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		
		//Verifcar se cor é diferente do jogador atual (peça do adversário)
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("the chosen piece is not yours");
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
	
	//Incrementar os turnos das jogadas
	private void nextTurn() {
		turn++;
		if (currentPlayer == Color.WHITE) {
			currentPlayer = Color.BLACK;
		}else {
			currentPlayer = Color.WHITE;
		}
	}
	
	//Devolve um opponent de uma cor
	private Color opponent(Color color) {
		if (color == Color.WHITE) {
			color = Color.BLACK;
		}
		else {
			color = Color.WHITE;
		}
		return color;
	}
	
	//Para localizar um rei de uma determinada cor
	private ChessPiece king(Color color) {
		//Procurar na lista de peças em jogo qual é que é o rei dessa cor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color ).collect(Collectors.toList());
		
		//Para cada peça 'piece' na lista 'list'  
		for (Piece piece : list) {
			
			//Se a lista 'piece' for uma instancia King significa que se encotrou o rei
			if (piece instanceof King) {
				return (ChessPiece) piece;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	
	//Para testar se um rei de uma terminada cor esta em check
	private boolean testCheck(Color color) {
		//Ver a posição do rei no formato de matriz
		Position kingPosition = king(color).getChessPosition().toPosition();
		
		//Lista das peças do oponente dessa cor - peças no tabuleiro filtradas com a cor do oponente desse rei
		List<Piece> opponentePieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		
		//Para cada peça 'piece' na lista opponentPieces testar se existe algum movimento possivel dessa peça que leva à posição do rei
		for (Piece piece : opponentePieces) {
			//Matriz de movimentos possiveis da peça adversária
			boolean[][] mat = piece.possibleMoves();
			
			//Se nesta matriz a posição correspondente à posição do rei for true significa que o rei está em check
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	//Verificar o CheckMate, ou seja, verificar se está em check e não existe nenhum movimento possível de qualquer peça da cor dele que o retire do check
	private boolean testCheckMate(Color color) {
		
		//Eliminar a possiblidade de não estar em check
		if (!testCheck(color)) {
			return false;
		}
		
		//Verificar se todas as peças da mesma cor não tiverem um movimento possível que o retire do check, então está em checkMate
		//Lista de peças com a mesma cor 'color'
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color ).collect(Collectors.toList());
		
		//Percorre todas as peças 'piece' pertencentes à lista
		for (Piece piece : list) {
			//Se existir alguma peça 'piece' na lista que possua um movimento que tire do check
			//Matriz com os movimentos possiveis
			boolean[][] mat = piece.possibleMoves();
			
			//Percorrer a matriz -> linhas i, colunas j
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColums(); j++) {
					//Movimento possivel -> mover a peça 'piece' para a matriz 'mat'
					if (mat[i][j]) {
						//Posição de origem da peça 'piece' -> retorna as posições no formato xadrez
						Position source = ((ChessPiece)piece).getChessPosition().toPosition();
						//Posição destino
						Position target = new Position(i, j);
						//Fazer o moviemento da peça 'piece' da origem para o destino
						Piece capturedPiece = makeMove(source, target);
						
						//Verificar se o rei da minha cor ainda está em check
						boolean testCheck = testCheck(color);
						
						//Desfazer movimento
						undoMove(source, target, capturedPiece);
						
						//Se não está em check significa que o movimento retirou o meu rei do check
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	//Colocar nova peça com as posições certas a1,a2...em vez de (0,0),(0,1), etc...
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		
		//Colocar a peça dentro da lista de peças no tabuleiro
		piecesOnTheBoard.add(piece);
	}
	
	//Colocar peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));

	}
}
