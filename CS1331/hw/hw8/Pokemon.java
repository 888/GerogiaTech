/**
 * Represents a Pokemon object. Each has a number, a name, and two elemental
 * types, chosen from the PokemonType enumeration.
 *
 * @author  Joe Rossi
 * @version 1.0
 */
public class Pokemon implements Comparable<Pokemon> {

    private int num;
    private String name;
    private PokemonType [] types;

    /**
     * Constructs a Pokemon object
     *
     * @param num   this Pokemon's unique number
     * @param name  this Pokemon's name
     * @param p this Pokemon's primary type
     * @param s this Pokemon's secondary type
     */
    public Pokemon(int num, String name, PokemonType p, PokemonType s) {
        this.num = num;
        this.name = name;
        this.types = new PokemonType[2];
        this.types[0] = p;
        this.types[1] = s;
    }

    @Override
    public int compareTo(Pokemon o) {
        return this.num - o.num;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Pokemon) {
            Pokemon p = (Pokemon) o;
            return this.getNumber() == p.getNumber();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int code = 0;
        code += num;
        code += types[0].ordinal();
        code += types[1].ordinal();
        code += name.hashCode();
        return code;
    }

    @Override
    public String toString() {
        return String.format("#%-6d: %-10s Primary Type: %-10s Secondary Type:"
            + " %-10s",
            getNumber(), getName(), getPrimaryType(), getSecondaryType());
    }

    /**
     * @return  the name of this Pokemon
     */
    public String getName() {
        return name;
    }

    /**
     * @return  the unique number of this Pokemon
     */
    public int getNumber() {
        return num;
    }

    /**
     * @return  the primary type of this Pokemon
     */
    public PokemonType getPrimaryType() {
        return types[0];
    }

    /**
     * @return  the secondary type of this Pokemon
     */
    public PokemonType getSecondaryType() {
        return types[1];
    }
}
