package nu.peg.news.tagesschau.model.mapper;

public interface Converter<In, Out> {
    public Out convert(In input);
}
