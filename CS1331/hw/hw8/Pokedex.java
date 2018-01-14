import java.util.Comparator;
/**
 * Represents a Pokedex - basically a Pokemon encyclopedia that adds new entries
 * when you encounter a Pokemon for the first time.
 * It also provides methods for organizing its information in useful ways.
 *
 * @author Joe Rossi
 * @version 1.0
 */
public class Pokedex {

    protected MySortedSet<Pokemon> collectedPokemon;
    /**
     * Constructs a Pokedex object by setting up the sorted set of Pokemon
     */
    public Pokedex() {
        collectedPokemon = new MySortedSet<Pokemon>();
    }

    @Override
    public String toString() {
        return collectedPokemon.toString();
    }

    /**
     * Adds a Pokemon to the sorted set
     *
     * @param p the Pokemon to be added
     * @return true if the pokemon was not already in the set, false otherwise
     */
    public boolean add(Pokemon p) {
        return collectedPokemon.add(p);
    }

    /**
     * Returns the number of Pokemon in the Pokedex
     *
     * @return  the number of Pokemon in the Pokedex
     */
    public int countPokemon() {
        return collectedPokemon.size();
    }

    /**
     * Clear the Pokedex and start over
     */
    public void clear() {
        collectedPokemon.clear();
    }

    /**
     * Returns a set of alphabetized Pokemon, using a lambda expression
     *
     * @return  the alphabetized set
     */
    public MySortedSet<Pokemon> listAlphabetically() {
        return collectedPokemon.sort(Comparator.comparing(Pokemon::getName));
    }

    /**
     * Returns a set of Pokemon grouped by type, using a lambda expression
     *
     * @return  the grouped by primary type set
     */
    public MySortedSet<Pokemon> groupByPrimaryType() {
        return collectedPokemon.sort((Pokemon p1, Pokemon p2) ->
            p1.getPrimaryType().ordinal() - p2.getPrimaryType().ordinal());
    }

    /**
     * Returns a set of all Pokemon of type t
     *
     * @param t the type we want listed
     * @return  the set of all Pokemon in the Pokedex of type t
     */
    public MySortedSet<Pokemon> listByType(PokemonType t) {
        return collectedPokemon.filter(p
            -> p.getPrimaryType() == t || p.getSecondaryType() == t);
    }

    /**
     * Returns a set of Pokemon with numbers in the range [start, end]
     *
     * @param start the first number in the new set
     * @param end   the last number in the new set
     * @return  the set containing all Pokemon in the Pokedex from [start, end]
     */
    public MySortedSet<Pokemon> listRange(int start, int end) {
        Pokemon startP = null;
        Pokemon endP = null;
        for (Pokemon p : collectedPokemon) {
            if (startP == null && p.getNumber() >= start) {
                startP = p;
                System.out.println("Start:" + p.getNumber());
            }
            if (endP == null && p.getNumber() >= end) {
                endP = p;
                System.out.println("End:" + p.getNumber());
                break;
            }
        }
        if (startP == null) {
            return null;
        }
        if (endP == null) {
            endP = collectedPokemon.last();
        }
        MySortedSet pokemonSet = collectedPokemon.subSet(startP, endP);
        pokemonSet.add(endP);
        return pokemonSet;
    }
}
