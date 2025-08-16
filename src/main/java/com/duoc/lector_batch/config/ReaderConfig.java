package com.duoc.lector_batch.config;

import java.time.LocalDate;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.duoc.lector_batch.model.Transaccion;
import com.duoc.lector_batch.model.Interes;
import com.duoc.lector_batch.model.CuentaAnual;

@Configuration
public class ReaderConfig {

    @Bean
    public FlatFileItemReader<Transaccion> transaccionReader() {
        FlatFileItemReader<Transaccion> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("transacciones.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<Transaccion> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "fecha", "monto", "tipo");
        lineMapper.setLineTokenizer(tokenizer);

        lineMapper.setFieldSetMapper(fieldSet -> {
            Transaccion t = new Transaccion();
            t.setId(fieldSet.readLong("id"));
            t.setFecha(LocalDate.parse(fieldSet.readString("fecha")));
            t.setMonto(fieldSet.readBigDecimal("monto"));
            t.setTipo(fieldSet.readString("tipo"));
            return t;
        });

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public FlatFileItemReader<Interes> interesReader() {
        FlatFileItemReader<Interes> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("intereses.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<Interes> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("cuentaId", "nombre", "saldo", "edad", "tipo");

        
        lineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<Interes> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(Interes.class);
        lineMapper.setFieldSetMapper(mapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public FlatFileItemReader<CuentaAnual> cuentaAnualReader() {
        FlatFileItemReader<CuentaAnual> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("cuentas_anuales.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<CuentaAnual> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("cuenta_id", "fecha", "transaccion", "monto", "descripcion");
        lineMapper.setLineTokenizer(tokenizer);

        lineMapper.setFieldSetMapper(fieldSet -> {
            CuentaAnual c = new CuentaAnual();
            c.setCuentaId(fieldSet.readLong("cuenta_id"));
            c.setAnio(Integer.parseInt(fieldSet.readString("fecha").substring(0, 4)));
            c.setSaldoFinal(fieldSet.readBigDecimal("monto"));
            c.setResumen(fieldSet.readString("descripcion"));
            return c;
        });

        reader.setLineMapper(lineMapper);
        return reader;
    }
}
