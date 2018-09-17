package Fraction;

import java.math.BigInteger;
/**
 *
 * @author Fair Nuri Aboshehwa
 */
public class Fraction {
    
    private BigInteger numerator;
    
    private BigInteger denominator;
    
    private void sanitizeSigns()
    {
        if(denominator.toString().charAt(0) == '-')
        {
            numerator = numerator.multiply(new BigInteger("-1"));
            denominator = denominator.multiply(new BigInteger("-1"));
        }
    }
    
    private void checkDivideByZero() throws ArithmeticException
    {
        if(denominator.toString().equals("0"))
            throw new ArithmeticException("Divide by zero");
    }
    
    private void makeZeroIfNecessary()
    {
        if(numerator.toString().equals("0"))
        {
            numerator = new BigInteger("0");
            denominator = new BigInteger("1");
        }
    }
    
    private BigInteger getCommonDenominator(Fraction f)
    {
        return denominator.multiply(f.getDenominator());
    }
    
    private void reduce()
    {
        BigInteger greatestCommonDivisor;
        BigInteger positiveNumerator = (numerator.toString().charAt(0) == '-' ? numerator.multiply(new BigInteger("-1")) : numerator);
        BigInteger positiveDenominator = (denominator.toString().charAt(0) == '-' ? denominator.multiply(new BigInteger("-1")) : denominator);
        if(positiveNumerator.toString().equals("0"))
        {
            return;
        }
        else if(positiveNumerator.compareTo(positiveDenominator) == -1)
        {
            greatestCommonDivisor = gcd(positiveDenominator, positiveNumerator);
        }
        else
        {
            greatestCommonDivisor = gcd(positiveNumerator, positiveDenominator);
        }
        numerator = numerator.divide(greatestCommonDivisor);
        denominator = denominator.divide(greatestCommonDivisor);
    }
    
    private BigInteger gcd(BigInteger a, BigInteger b)
    {
        if(BigInteger.ZERO.compareTo(b) == 0)
            return a;
        return gcd(b, a.mod(b));
    }
    
    @Override
    public String toString()
    {
        if(numerator.toString().equals("0"))
            return "0";
        if(numerator.toString().equals(denominator.toString()))
            return "1";
        if(numerator.toString().charAt(0) == '-' && numerator.toString().substring(1, numerator.toString().length()).equals(denominator.toString()))
            return "-1";
        if(denominator.toString().equals("1"))
            return numerator.toString();
        return numerator.toString() + "/" + denominator.toString();
    }
    
    public Fraction()
    {
        numerator = new BigInteger("0");
        denominator = new BigInteger("1");
    }
    
    public Fraction(String numerator)
    {
        this.numerator = new BigInteger(numerator);
        this.denominator = new BigInteger("1");
        checkDivideByZero();
        makeZeroIfNecessary();
        sanitizeSigns();
        reduce();
    }
    
    public Fraction(BigInteger numerator)
    {
        this.numerator = new BigInteger(numerator.toString());
        this.denominator = new BigInteger("1");
        checkDivideByZero();
        makeZeroIfNecessary();
        sanitizeSigns();
        reduce();
    }
    
    public Fraction(Fraction f)
    {
        this.numerator = new BigInteger(f.getNumerator().toString());
        this.denominator = new BigInteger(f.getDenominator().toString());
        checkDivideByZero();
        makeZeroIfNecessary();
        sanitizeSigns();
        reduce();
    }
    
    public Fraction(BigInteger numerator, BigInteger denominator)
    {
        this.numerator = new BigInteger(numerator.toString());
        this.denominator = new BigInteger(denominator.toString());
        checkDivideByZero();
        makeZeroIfNecessary();
        sanitizeSigns();
        reduce();
    }
    
    public Fraction(String numerator, String denominator)
    {
        this.numerator = new BigInteger(numerator);
        this.denominator = new BigInteger(denominator);
        checkDivideByZero();
        makeZeroIfNecessary();
        sanitizeSigns();
        reduce();
    }
    
    public BigInteger getNumerator()
    {
        return numerator;
    }
    
    public BigInteger getDenominator()
    {
        return denominator;
    }
    
    public void setNumerator(BigInteger numerator)
    {
        this.numerator = numerator;
    }
    
    public void setDenominator(BigInteger denominator)
    {
        this.denominator = denominator;
    }
    
    public Fraction multiply(Fraction f)
    {
        Fraction left = new Fraction(numerator, f.getDenominator());
        Fraction right= new Fraction(f.getNumerator(), denominator);
        left.setNumerator(left.getNumerator().multiply(right.getNumerator()));
        left.setDenominator(left.getDenominator().multiply(right.getDenominator()));
        return new Fraction(left);
    }
    
    public Fraction divide(Fraction f)
    {
        Fraction left = new Fraction(numerator, denominator);
        Fraction right = new Fraction(f.getDenominator(), f.getNumerator());
        return left.multiply(right);
    }
    
    public Fraction subtract(Fraction f)
    {
       if(toString().equals(f.toString()))
       {
           return new Fraction();
       }
       
       if(denominator.toString().equals(f.getDenominator().toString()))
       {
           return new Fraction(numerator.subtract(f.getNumerator()).toString(), denominator.toString());
       }
       
       BigInteger numerator, denominator;
       Fraction result;
       if(this.denominator.toString().equals("1"))
       {
           numerator = this.numerator.multiply(f.getDenominator());
           denominator = f.getDenominator();
           result = new Fraction(numerator.subtract(f.getNumerator()), new BigInteger(denominator.toString()));
       }
       else if(f.getDenominator().toString().equals("1"))
       {
           numerator = f.getNumerator().multiply(this.denominator);
           denominator = new BigInteger(this.denominator.toString());
           result = new Fraction(this.numerator.subtract(numerator), denominator);
       }
       else
       {
           BigInteger commonDenominator = getCommonDenominator(f);
           numerator = this.numerator.multiply(commonDenominator.divide(this.denominator));
           denominator = f.getNumerator().multiply(commonDenominator.divide(f.getDenominator()));
           result = new Fraction(numerator.subtract(denominator), commonDenominator);
       }
       return result;
    }
    
    public Fraction add(Fraction f)
    {
       if(denominator.toString().equals(f.getDenominator().toString()))
       {
           return new Fraction(numerator.add(f.getNumerator()).toString(), denominator.toString());
       }
       
       BigInteger numerator, denominator;
       Fraction result;
       if(this.denominator.toString().equals("1"))
       {
           numerator = this.numerator.multiply(f.getDenominator());
           denominator = f.getDenominator();
           result = new Fraction(numerator.add(f.getNumerator()), new BigInteger(denominator.toString()));
       }
       else if(f.getDenominator().toString().equals("1"))
       {
           numerator = f.getNumerator().multiply(this.denominator);
           denominator = new BigInteger(this.denominator.toString());
           result = new Fraction(this.numerator.add(numerator), denominator);
       }
       else
       {
           BigInteger commonDenominator = getCommonDenominator(f);
           numerator = this.numerator.multiply(commonDenominator.divide(this.denominator));
           denominator = f.getNumerator().multiply(commonDenominator.divide(f.getDenominator()));
           result = new Fraction(numerator.add(denominator), commonDenominator);
       }
       return result;
    }
}
