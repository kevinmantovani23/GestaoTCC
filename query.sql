create database bancatcc


use bancatcc

 --Trigger para verificar se os alunos tem o percentual de conclusão necessário para o tcc
CREATE TRIGGER trg_Validar_TCC
ON apresentacao
AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @codigoGrupo INT
    DECLARE @tipoTCC NVARCHAR(4)
    
    SELECT @codigoGrupo = codigoGrupo, @tipoTCC = tipoTcc
    FROM INSERTED


    IF @tipoTCC = 'TCC1'
    BEGIN
        IF EXISTS (
            SELECT *
            FROM aluno a
            WHERE a.codigoGrupo = @codigoGrupo 
            AND a.percentualConclusao < 75
        )
        BEGIN
            RAISERROR('Todos os alunos devem ter pelo menos 75% de conclusão para apresentar TCC1.', 16, 1)
            ROLLBACK TRANSACTION
            RETURN
        END
    END
    ELSE IF @tipoTCC = 'TCC2'
    BEGIN
        IF EXISTS (
            SELECT *
            FROM aluno a
            WHERE a.codigoGrupo = @codigoGrupo 
            AND a.percentualConclusao < 90
        )
        BEGIN
            RAISERROR('Todos os alunos devem ter pelo menos 90% de conclusão para apresentar TCC2.', 16, 1)
            ROLLBACK TRANSACTION
            RETURN
        END
    END
END

--Trigger grupo com mais de 4 alunos
CREATE TRIGGER trg_Validar_Limite_Alunos
ON aluno --Valida o aluno porque ele que possui a chave secundária
AFTER UPDATE
AS
BEGIN
    DECLARE @codigoGrupo INT
    SELECT @codigoGrupo = codigoGrupo FROM INSERTED
    
    IF (SELECT COUNT(*) FROM aluno WHERE codigoGrupo = @codigoGrupo) > 4
    BEGIN
        RAISERROR('Um grupo não pode ter mais de 4 alunos.', 16, 1)
        ROLLBACK TRANSACTION
        RETURN
    END
    
END

--Trigger banca deve ter exatos 3 professores 
CREATE TRIGGER trg_Validar_Numero_Professores
ON banca
AFTER INSERT
AS
BEGIN
    DECLARE @codigoApresentacao INT

    SELECT @codigoApresentacao = apresentacaoCodigo FROM INSERTED

    DECLARE @numProfessores INT
    SELECT @numProfessores = COUNT(*)
    FROM banca
    WHERE apresentacaoCodigo = @codigoApresentacao

    IF @numProfessores != 3
    BEGIN
		DELETE FROM banca
		WHERE apresentacaoCodigo = @codigoApresentacao
		DELETE FROM apresentacao
		WHERE codigo = @codigoApresentacao
		
        RAISERROR('Uma apresentação deve ter exatamente 3 professores na banca.', 16, 1)
        
    END
END


--Apresentação agendada em ate 1 semana

CREATE TRIGGER trg_Validar_Data_Apresentacao
ON apresentacao
AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @dataApresentacao DATE
	
    SELECT @dataApresentacao = dataApresentacao FROM INSERTED
	 
    IF DATEDIFF(DAY, GETDATE(), @dataApresentacao) > 7
    BEGIN
        RAISERROR('A data da apresentação deve ser agendada em até 1 semana.', 16, 1)
        ROLLBACK TRANSACTION
    END
END

--Só permiti trocar a data da apresentação e nota, pois faz mais sentido deletar ela e criar outra caso seja alterações na banca ou grupo.
CREATE TRIGGER trg_Atualizar_ApenasData 
ON apresentacao
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @dataApresentacao DATE
	DECLARE @codigo INT
	DECLARE @nota FLOAT

    SELECT @dataApresentacao = dataApresentacao FROM INSERTED
	SELECT @codigo = codigo FROM INSERTED
	SELECT @nota = nota FROM INSERTED

    UPDATE apresentacao
	SET dataApresentacao = @dataApresentacao,
	nota = @nota
	WHERE codigo = @codigo
END

--UDF com quantidades de grupos de um professor
CREATE FUNCTION dbo.fn_QuantidadeGruposPorProfessor(@codigoProfessor INT)
RETURNS INT
AS
BEGIN
    DECLARE @qtdGrupos INT

    SELECT @qtdGrupos = COUNT(*) 
	FROM grupo
    WHERE codigoProfessor = @codigoProfessor

    RETURN @qtdGrupos
END


CREATE TRIGGER trg_Deletar_Cascata
ON apresentacao
INSTEAD OF DELETE
AS
BEGIN
	DECLARE @codigo INT

	SELECT @codigo = codigo FROM INSERTED

	DELETE FROM banca
	WHERE apresentacaoCodigo = @codigo

	DELETE FROM apresentacao
	WHERE codigo = @codigo
END

