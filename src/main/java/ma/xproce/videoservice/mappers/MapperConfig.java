package ma.xproce.videoservice.mappers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class MapperConfig {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Bean
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        // STRICT évite les ambiguïtés entre video.name et video.creator.name
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<String, LocalDate> toDate = ctx ->
                ctx.getSource() == null ? null : LocalDate.parse(ctx.getSource(), FORMAT);
        Converter<LocalDate, String> toText = ctx ->
                ctx.getSource() == null ? null : ctx.getSource().format(FORMAT);

        mapper.addConverter(toDate, String.class, LocalDate.class);
        mapper.addConverter(toText, LocalDate.class, String.class);
        return mapper;
    }
}