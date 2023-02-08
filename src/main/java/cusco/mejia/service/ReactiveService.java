package cusco.mejia.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import cusco.mejia.dto.DataDto;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class ReactiveService {
    

    public Uni<DataDto> procesar() {
        Uni<DataDto> dtouni = info();
        return procesarCuentas(dtouni)
            .onItem().transformToUni(dto -> procesarTarjetas(Uni.createFrom().item(dto)));
    }


    private Uni<DataDto> procesarCuentas(Uni<DataDto> dtouni) {
        return dtouni.map(dto -> {
            List<Map<String, String>> cuentas = dto.getCuentas();
            List<Map<String, String>> accounts = new ArrayList<>();
            for (Map<String, String> cuenta : cuentas) {
               Map<String, String> account = new HashMap<>();
               account.put("account", cuenta.get("cuenta"));
                account.put("status", cuenta.get("estado"));
                account.put("type", cuenta.get("tipo"));
                account.put("currency", cuenta.get("moneda"));
                account.put("balance", cuenta.get("saldo"));
                accounts.add(account);
            }
            dto.getCtl().put("accounts", accounts);
            return dto;
        });
    }

    private Uni<DataDto> procesarTarjetas(Uni<DataDto> dtouni) {
        return dtouni.map(dto -> {
            List<Map<String, String>> tarjetas = dto.getTarjetas();
            List<Map<String, String>> cards = new ArrayList<>();
            for (Map<String, String> tarjeta : tarjetas) {
                Map<String, String> card = new HashMap<>();
                card.put("card", tarjeta.get("tarjeta"));
                card.put("status", tarjeta.get("estado"));
                card.put("type", tarjeta.get("tipo"));
                card.put("currency", tarjeta.get("moneda"));
                card.put("balance", tarjeta.get("saldo"));
                cards.add(card);
            }
            dto.getCtl().put("cards", cards);
            return dto;
        });
    }


    private Uni<DataDto> info() {
        DataDto dto = new DataDto();
        dto.setCan("WEB");
        dto.setSub("19");
        dto.setTrn("4006");
        dto.setCode("000");
        dto.setMessage("OK");
        List<Map<String, String>> cuentas = new ArrayList<>();
        List<Map<String, String>> tarjetas = new ArrayList<>();
        Map<String, String> cuenta_1 = Map.of(
            "cuenta", "1234567890", 
            "estado", "activa",
            "tipo", "ahorro",
            "moneda", "usd",
            "saldo", "1000.00"
        );
        Map<String, String> cuenta_2 = Map.of(
            "cuenta", "0987654321", 
            "estado", "activa",
            "tipo", "corriente",
            "moneda", "pen",
            "saldo", "2000.00"
        );
        Map<String, String> tarjeta_1 = Map.of(
            "tarjeta", "1234567890", 
            "estado", "activa",
            "tipo", "credito",
            "moneda", "usd",
            "saldo", "1000.00"
        );
        Map<String, String> tarjeta_2 = Map.of(
            "tarjeta", "0987654321", 
            "estado", "activa",
            "tipo", "debito",
            "moneda", "pen",
            "saldo", "2000.00"
        );
        cuentas.add(cuenta_1);
        cuentas.add(cuenta_2);
        tarjetas.add(tarjeta_1);
        tarjetas.add(tarjeta_2);
        dto.setCuentas(cuentas);
        dto.setTarjetas(tarjetas);
        return Uni.createFrom().item(dto);
    }


}
