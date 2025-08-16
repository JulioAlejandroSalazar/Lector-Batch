package com.duoc.lector_batch.config;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.duoc.lector_batch.model.Transaccion;
import com.duoc.lector_batch.model.Interes;
import com.duoc.lector_batch.model.CuentaAnual;

@Configuration
public class WriterConfig {

    @Bean
    public JdbcBatchItemWriter<Transaccion> transaccionWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Transaccion> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO transacciones (id, fecha, monto, tipo, observacion) VALUES (:id, :fecha, :monto, :tipo, :observacion)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }

    @Bean
    public JdbcBatchItemWriter<Interes> interesWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Interes> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql(
        "UPDATE intereses SET interes_aplicado = :interesAplicado, saldo_final = :saldoFinal WHERE cuenta_id = :cuentaId"

        );
        

        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }
    

    @Bean
    public JdbcBatchItemWriter<CuentaAnual> cuentaAnualWriter(DataSource dataSource) {
        JdbcBatchItemWriter<CuentaAnual> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO cuentas_anuales (cuenta_id, anio, saldo_final, resumen) " +
                      "VALUES (:cuentaId, :anio, :saldoFinal, :resumen)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }
}
