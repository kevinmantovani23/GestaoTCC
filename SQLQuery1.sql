create database bancatcc
use bancatcc

 --Trigger para verificar se os alunos tem o percentual de conclusão necessário para o tcc
CREATE TRIGGER trg_Validar_TCC
ON apresentacao
INSTEAD OF INSERT, UPDATE
AS
BEGIN
    DECLARE @codigoGrupo INT
    DECLARE @tipoTCC NVARCHAR(4)
    
    SELECT @codigoGrupo = codigoGrupo, @tipoTCC = tipoTcc
    FROM INSERTED


    IF @tipoTCC = 'TCC1'
    BEGIN
        IF EXISTS (
            SELECT 1
            FROM aluno a
            WHERE a.ra IN (SELECT ra FROM grupo WHERE codigo = @codigoGrupo) 
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
            SELECT 1
            FROM aluno a
            WHERE a.ra IN (SELECT ra FROM grupo WHERE codigo = @codigoGrupo) 
            AND a.percentualConclusao < 90
        )
        BEGIN
            RAISERROR('Todos os alunos devem ter pelo menos 90% de conclusão para apresentar TCC2.', 16, 1)
            ROLLBACK TRANSACTION
            RETURN
        END
    END


    INSERT INTO apresentacao (codigoGrupo, dataApresentacao, tipoTcc, nota)
    SELECT codigoGrupo, dataApresentacao, tipoTcc, nota
    FROM INSERTED
END

--Trigger grupo com mais de 4 alunos
CREATE TRIGGER trg_Validar_Limite_Alunos
ON grupo
INSTEAD OF INSERT, UPDATE
AS
BEGIN
    DECLARE @codigoGrupo INT
    
    SELECT @codigoGrupo = codigo FROM INSERTED
    
    IF (SELECT COUNT(*) FROM aluno WHERE codigoGrupo = @codigoGrupo) >= 4
    BEGIN
        RAISERROR('Um grupo não pode ter mais de 4 alunos.', 16, 1)
        ROLLBACK TRANSACTION
        RETURN
    END

    IF EXISTS (SELECT * FROM grupo WHERE codigo = @codigoGrupo)
    BEGIN
        UPDATE grupo
        SET nome = (SELECT nome FROM INSERTED WHERE codigo = @codigoGrupo)
        WHERE codigo = @codigoGrupo
    END
    ELSE
    BEGIN 
        INSERT INTO grupo (codigo, nome, codigoProfessor)
        SELECT codigo, nome, codigoProfessor FROM INSERTED
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
        RAISERROR('Uma apresentação deve ter exatamente 3 professores na banca.', 16, 1)
        ROLLBACK TRANSACTION
    END
END

--Apresentação agendada em ate 3 semanas

CREATE TRIGGER trg_Validar_Data_Apresentacao
ON apresentacao
AFTER INSERT, UPDATE
AS
BEGIN
    DECLARE @dataApresentacao DATE
	
    SELECT @dataApresentacao = dataApresentacao FROM INSERTED
	 
    IF DATEDIFF(DAY, GETDATE(), @dataApresentacao) > 21
    BEGIN
        RAISERROR('A data da apresentação deve ser agendada em até 3 semanas a partir de hoje.', 16, 1)
        ROLLBACK TRANSACTION
    END
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

