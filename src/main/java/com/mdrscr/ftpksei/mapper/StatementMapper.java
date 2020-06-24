package com.mdrscr.ftpksei.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.mdrscr.ftpksei.persist.model.BejStatementStaging;
import com.mdrscr.ftpksei.persist.model.StatementKsei;

@Mapper
public interface StatementMapper {

	StatementMapper INSTANCE = Mappers.getMapper(StatementMapper.class );
	
	@Mapping(source="acctno", target="ac")
	@Mapping(source="trntyp", target="trxtype")
	@Mapping(source="drorcr", target="dc")
	@Mapping(source="trnamt", target="cashVal")
	@Mapping(source="trnref", target="statLineExtRef")
	@Mapping(source="valdat", target="valdate")
	@Mapping(source="opnbal", target="openbal")
	@Mapping(source="clsbal", target="closeBal")
	@Mapping(source="acnote", target="notes")
	@Mapping(source="trndsc", target="description")
	StatementKsei stmtStgToStatementMsg(BejStatementStaging stmt);
}
