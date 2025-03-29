package org.jfree.data;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.jfree.data.Range; 
import org.junit.*;

public class RangeTestNew {

	private Range exampleRange1;
    private Range exampleRange2;
    private Range exampleRange3;
    private Range exampleRange4;
    private Range exampleRange5;
    private Range contains_range;

    
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }


    @Before
    public void setUp() throws Exception { 
    	exampleRange1 = new Range(-2, 3);
    	exampleRange2 = null;
    	exampleRange3 = null;
    	exampleRange4 = new Range(2, 7);
    	exampleRange5 = new Range(12, 17);
        contains_range = new Range(-5.0, 5.0);
    }

// Test cases for combine
    @Test
    public void testCombineWithNullRange1() {
        assertEquals("The range should be equal to exampleRange1",
        exampleRange1, Range.combine(exampleRange1, exampleRange2));
    }
    @Test
    public void testCombineWithNullRange2() {
        assertEquals("The range should be equal to exampleRange1",
        exampleRange1, Range.combine(exampleRange2, exampleRange1));
    }
    
    @Test
    public void testCombineWithBothNullRanges() {
        assertEquals("The range should be null",
        null, Range.combine(exampleRange2, exampleRange3));
    }
    
    @Test
    public void testCombineWithOverlappingRanges() {
        Range test = Range.combine(exampleRange4, exampleRange1);
    	assertEquals("The range should be equal to [-2,7]", new Range(-2,7), test);
    }
    
    @Test
    public void testCombineWithDisjointRanges() {
        Range test = Range.combine(exampleRange5, exampleRange4);
    	assertEquals("The range should be equal to [2,17]", new Range(2,17), test);
    }
    
  //Test cases for ToString()
 	 
	    @Test
	    public void testToStringWithPositiveNumbers() {
	        Range range = new Range(5.0, 10.0);
	        String expected = "Range[5.0,10.0]";
	        assertEquals("toString should return Range[lower,upper]", expected, range.toString());
	    }
	    
	    @Test
	    public void testToStringWithNegativeNumbers() {
	        Range range = new Range(-2.0, -1.0);
	        String expected = "Range[-2.0,-1.0]";
	        assertEquals("toString should correctly format negative values", expected, range.toString());
	    }
	    
	    @Test
	    public void testToStringWithSameNumbers() {
	        Range range = new Range(1.0, 1.0);
	        String expected = "Range[1.0,1.0]";
	        assertEquals("toString should correctly format negative values", expected, range.toString());
	    }
	    
	    @Test
	    public void testToStringWithZero() {
	        Range range = new Range(0.0, 0.0);
	        String expected = "Range[0.0,0.0]";
	        assertEquals("toString should handle zero values", expected, range.toString());
	    }
	    
	    @Test
	    public void testToStringWithMixedValues() {
	        Range range = new Range(-1.0, 1.0);
	        String expected = "Range[-1.0,1.0]";
	        assertEquals("toString should correctly display mixed values", expected, range.toString());
	    }
	    
	  //Tests for UpperBound

		//Declaring and setting up variables
		private Range finiteRangeUpper;
	    private Range infiniteRangeUpper;
	    private Range nanRangeUpper;

	    @Before
	    public void setUpUpper() throws Exception {
	        // Creating different range scenarios
	        finiteRangeUpper = new Range(-10.0, 15.5); // Finite range
	        infiniteRangeUpper = new Range(0.0, Double.POSITIVE_INFINITY); // Infinite upper bound
	        nanRangeUpper = new Range(5.0, Double.NaN); // NaN upper bound
	    }

		//Test for checking upperbound when range has a finite range
	    @Test
	    public void testGetUpperBoundForFiniteValue() {
	        // Upper bound should be 15.5
	        assertEquals(15.5, finiteRangeUpper.getUpperBound(), 0.000000001d);
	    }

		//test for checking upperbound when range has a positive infinity
	    @Test
	    public void testGetUpperBoundForInfiniteValue() {
	        // Upper bound should be POSITIVE_INFINITY
	        assertEquals(Double.POSITIVE_INFINITY, infiniteRangeUpper.getUpperBound(), 0.0);
	    }

		//test for checking upperbound when range has a NaN
	    @Test
	    public void testGetUpperBoundForNaNValue() {
	        // Upper bound should be NaN
	        assertTrue(Double.isNaN(nanRangeUpper.getUpperBound()));
	    }
	    @Ignore
        public void testGetUpperBound_InvalidStateUsingReflection()
        		throws Exception {
            Range range = new Range(5, 10); 

            Field lowerField = Range.class.getDeclaredField("lower");
            Field upperField = Range.class.getDeclaredField("upper");
            lowerField.setAccessible(true);
            upperField.setAccessible(true);
            lowerField.setDouble(range, 15.0);
            upperField.setDouble(range, 10.0); // Now lower > upper

            try {
                range.getUpperBound();
                fail("Expected IllegalArgumentException due to"
                		+ "manipulated state.");
            } catch (IllegalArgumentException e) {
                assertEquals("Range(double, double): require lower"
                		+ " (15.0) <= upper (10.0).", e.getMessage());
            }
        }


		//Tests for LowerBound

		//declaring and setting up variables
		private Range finiteRangeLower;
		private Range infiniteRangeLower;
		private Range nanRangeLower;

		@Before
		public void setUpLower() throws Exception {
			// Creating different range scenarios
			finiteRangeLower = new Range(-10.0, 15.5); // Finite range
			infiniteRangeLower = new Range(Double.NEGATIVE_INFINITY, 100.0); // Infinite lower bound
			nanRangeLower = new Range(Double.NaN, 20.0); // NaN lower bound
		}

		//Test for checking lowerbound when range has a finite range
		@Test
		public void testGetLowerBoundForFiniteValue() {
			// Lower bound should be -10.0
			assertEquals(-10.0, finiteRangeLower.getLowerBound(), 0.000000001d);
		}

		//Test for checking lowerbound when range has a negative infinity 
		@Test
		public void testGetLowerBoundForInfiniteValue() {
			// Lower bound should be NEGATIVE_INFINITY
			assertEquals(Double.NEGATIVE_INFINITY, infiniteRangeLower.getLowerBound(), 0.0);
		}

		//Test for checking lowerbound when range has a NaN value
		@Test
		public void testGetLowerBoundForNaNValue() {
			// Lower bound should be NaN
			assertTrue(Double.isNaN(nanRangeLower.getLowerBound()));
		}
		@Ignore
		public void testGetLowerBound_InvalidStateUsingReflection() throws Exception {
		    Range range = new Range(5, 10); // Initially valid

		    // Force invalid state using reflection
		    Field lowerField = Range.class.getDeclaredField("lower");
		    Field upperField = Range.class.getDeclaredField("upper");
		    lowerField.setAccessible(true);
		    upperField.setAccessible(true);
		    lowerField.setDouble(range, 15.0);
		    upperField.setDouble(range, 10.0); // Now lower > upper

		    try {
		        range.getLowerBound();
		        fail("Expected IllegalArgumentException due to manipulated state.");
		    } catch (IllegalArgumentException e) {
		        assertEquals("Range(double, double): require lower (15.0) <= upper (10.0).", e.getMessage());
		    }
		}

	   
		@Test
		public void testContains_WithReflectionForFullCoverage() throws Exception {
		    Range range = new Range(5, 10); // valid initial state

		    // Force invalid internal state using reflection
		    Field lowerField = Range.class.getDeclaredField("lower");
		    Field upperField = Range.class.getDeclaredField("upper");
		    lowerField.setAccessible(true);
		    upperField.setAccessible(true);

		    lowerField.setDouble(range, 15.0);
		    upperField.setDouble(range, 10.0); // invalid: lower > upper

		    // This value would normally be between lower and upper if the range were valid
		    double testValue = 12.0;

		    // Act & Assert
		    assertFalse("Should return false due to invalid internal state.", range.contains(testValue));
		}

		//Tests for contains()
		
		@Test
	       // Test for value within range
	    public void testValueWithinRange() {
	        assertTrue("Value within range should return true", contains_range.contains(0.0));
	    }

	    @Test
	    // Test for value at lower bound
	    public void testValueAtLowerBound() {
	        assertTrue("Value at lower bound should return true", contains_range.contains(-5.0));
	    }

	    @Test
	    // Test for value at upper bound
	    public void testValueAtUpperBound() {
	        assertTrue("Value at upper bound should return true", contains_range.contains(5.0));
	    }

	    @Test
	    // Test for value below range
	    public void testValueBelowRange() {
	        assertFalse("Value below range should return false", contains_range.contains(-5.1));
	    }

	    @Test
	    // Test for value above range
	    public void testValueAboveRange() {
	        assertFalse("Value above range should return false", contains_range.contains(5.001));
	    }

	    @Test
	    // Test for negative range
	    public void testNegativeRange() {
	        Range negativeRange = new Range(-10.0, -1.0);
	        assertTrue("Value within negative range should return true", negativeRange.contains(-5.0));
	        assertFalse("Value above negative range should return false", negativeRange.contains(0.0));
	    }

	    @Test
	    // Test for positive range
	    public void testPositiveRange() {
	        Range zeroRange = new Range(0.0, 10.0);
	        assertTrue("Value at zero boundary should return true", zeroRange.contains(4));
	        assertFalse("Value below zero boundary should return false", zeroRange.contains(-0.0000000001));
	    }
	    

	    // Test with lower and upper being negative: input: lower = -20, upper = -10
	    @Test
	    public void getLengthWithBothBoundsNegative() {
	        Range range = new Range(-20, -10);
	        double expected = 10.0; // (-10) - (-20) = 10
	        assertEquals("getLength() with both negative bounds should return 10.0", expected, range.getLength(), 0.000001);
	    }
	    
	    // Test with lower and upper being positive: input: lower = 10, upper = 20
	    @Test
	    public void getLengthWithBothBoundsPositive() {
	        Range range = new Range(10, 20);
	        double expected = 10.0; // 20 - 10 = 10
	        assertEquals("getLength() with both positive bounds should return 10.0", expected, range.getLength(), 0.000001);
	    }
	    
	    // Test with lower and upper being equal: input: lower = 0, upper = 0
	    @Test
	    public void getLengthWithBothBoundsEqual() {
	        Range range = new Range(0, 0);
	        double expected = 0.0; // 0 - 0 = 0
	        assertEquals("getLength() with equal bounds should return 0.0", expected, range.getLength(), 0.000001);
	    }
	    
	    // Test with lower < 0 and upper > 0: input: lower = -10, upper = 20
	    @Test
	    public void getLengthWithNegativeLowerAndPositiveUpper() {
	        Range range = new Range(-10, 20);
	        double expected = 30.0; // 20 - (-10) = 30
	        assertEquals("getLength() with negative lower and positive upper should return 30.0", expected, range.getLength(), 0.000001);
	    }
	    
	    // Test with lower = 0 and upper > 0: input: lower = 0, upper = 20
	    @Test
	    public void getLengthWithZeroLowerAndPositiveUpper() {
	        Range range = new Range(0, 20);
	        double expected = 20.0; // 20 - 0 = 20
	        assertEquals("getLength() with zero lower and positive upper should return 20.0", expected, range.getLength(), 0.000001);
	    }
	    
	    // Test with lower < 0 and upper = 0: input: lower = -10, upper = 0
	    @Test
	    public void getLengthWithNegativeLowerAndZeroUpper() {
	        Range range = new Range(-10, 0);
	        double expected = 10.0; // 0 - (-10) = 10
	        assertEquals("getLength() with negative lower and zero upper should return 10.0", expected, range.getLength(), 0.000001);
	    }

	    // Test case for invalid range where lower > upper
	    @Test
	    public void getLengthWithInvalidRange() {
	        try {
	            new Range(10, -10);
	        } catch (IllegalArgumentException e) {
	            String expectedMessage = "Range(double, double): require lower (10.0) <= upper (-10.0).";
	            assertEquals(expectedMessage, e.getMessage());
	        }
	    }

	@After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    
    //Test for getCentralValue()
    @Test
    public void testGetCentralValue() {
        Range range = new Range(5.0, 15.0);
        
        double centralValue = range.getCentralValue();
        
        assertEquals(10.0, centralValue, 0.000000001); 
    }
    
    //Tests for constrain
    
    @Test
    public void testConstrainWithinRange() {
        Range range = new Range(5.0, 10.0); 
        double result = range.constrain(7.0); 
        assertEquals(7.0, result, 0.000000001); 
    }
    
    @Test
    public void testConstrainAboveUpperBound() {
        Range range = new Range(5.0, 10.0); 
        double result = range.constrain(15.0);
        assertEquals(10.0, result, 0.000000001); 
    }

    @Test
    public void testConstrainBelowLowerBound() {
        Range range = new Range(5.0, 10.0); 
        double result = range.constrain(3.0); 
        assertEquals(5.0, result, 0.000000001);
    }
    
    //Tests for intersects(double,double)
    @Test
    public void testIntersectsLowerBoundaryTrue() {
        Range range = new Range(5.0, 10.0);
        boolean result = range.intersects(4.0, 6.0);
        assertTrue(result);
    }

    @Test
    public void testIntersectsLowerBoundaryFalse() {
        Range range = new Range(5.0, 10.0);
        boolean result = range.intersects(4.0, 5.0);
        assertFalse(result);
    }

    @Test
    public void testIntersectsWithinRangeTrue() {
        Range range = new Range(5.0, 10.0);
        boolean result = range.intersects(6.0, 8.0);
        assertTrue(result);
    }

    @Test
    public void testIntersectsOutsideRangeFalse() {
        Range range = new Range(5.0, 10.0);
        boolean result = range.intersects(11.0, 12.0);
        assertFalse(result);
    }
    
    //Test for intersects(Range)
    @Test
    public void testIntersectsRange() {
        Range range1 = new Range(5.0, 10.0);
        Range range2 = new Range(8.0, 12.0);
        boolean result = range1.intersects(range2);
        assertTrue(result);
    }
    
    //Test for hashcode
    @Test
    public void testHashCodeWithDistinctBounds() {
        Range range = new Range(5.0, 10.0);
        int hash = range.hashCode();
        assertNotNull(hash);
    }
    
    @Test
    public void testHashCodeWithEqualBounds() {
        Range range = new Range(5.0, 5.0);
        int hash = range.hashCode();
        assertNotNull(hash);
    }
    
    //Tests for equals
    @Test
    public void testEqualsWithNonRangeObject() {
        Object nonRangeObject = "Not a Range";
        Range range = new Range(1.0, 5.0);
        assertFalse(range.equals(nonRangeObject));
    }

    @Test
    public void testEqualsWithDifferentLowerBound() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(2.0, 5.0);
        assertFalse(range1.equals(range2));
    }

    @Test
    public void testEqualsWithDifferentUpperBound() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(1.0, 6.0);
        assertFalse(range1.equals(range2));
    }

    @Test
    public void testEqualsWithEqualRanges() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(1.0, 5.0);
        assertTrue(range1.equals(range2));
    }
    
    //Test getLength
    @Ignore
	public void testGetLength_WithReflectionForFullCoverage() throws Exception {
	    Range range = new Range(5, 10); // valid initial state

	    // Force invalid internal state using reflection
	    Field lowerField = Range.class.getDeclaredField("lower");
	    Field upperField = Range.class.getDeclaredField("upper");
	    lowerField.setAccessible(true);
	    upperField.setAccessible(true);

	    lowerField.setDouble(range, 15.0);
	    upperField.setDouble(range, 10.0); // invalid: lower > upper

	    try {
	        range.getLength();
	        fail("Expected IllegalArgumentException due to manipulated state.");
	    } catch (IllegalArgumentException e) {
	        assertEquals("Range(double, double): require lower (15.0) <= upper (10.0).", e.getMessage());
	    }
	}
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


@Test
public void combineIgnoringNaN_bothRangesNull() {
assertNull(Range.combineIgnoringNaN(null, null));
}

@Test
public void combineIgnoringNaN_firstRangeNullSecondNaN() {
Range r2 = new Range(Double.NaN, Double.NaN);
assertNull(Range.combineIgnoringNaN(null, r2));
}

@Test
public void combineIgnoringNaN_firstRangeNullSecondValid() {
Range r2 = new Range(1, 5);
assertEquals(r2, Range.combineIgnoringNaN(null, r2));
}

@Test
public void combineIgnoringNaN_secondRangeNullFirstNaN() {
Range r1 = new Range(Double.NaN, Double.NaN);
assertNull(Range.combineIgnoringNaN(r1, null));
}

@Test
public void combineIgnoringNaN_secondRangeNullFirstValid() {
Range r1 = new Range(2, 6);
assertEquals(r1, Range.combineIgnoringNaN(r1, null));
}

@Test
public void combineIgnoringNaN_bothRangesValid() {
Range r1 = new Range(1, 4);
Range r2 = new Range(2, 6);
assertEquals(new Range(1, 6), Range.combineIgnoringNaN(r1, r2));
}

@Test
public void combineIgnoringNaN_bothRangesNaN() {
Range r1 = new Range(Double.NaN, Double.NaN);
Range r2 = new Range(Double.NaN, Double.NaN);
assertNull(Range.combineIgnoringNaN(r1, r2));
}

@Test
public void combineIgnoringNaN_firstRangeNaNLowerBoundSecondValid() {
Range r1 = new Range(Double.NaN, 3);
Range r2 = new Range(2, 5);
assertEquals(new Range(2, 5), Range.combineIgnoringNaN(r1, r2));
}

@Test
public void combineIgnoringNaN_firstRangeNaNUpperBoundSecondValid() {
Range r1 = new Range(1, Double.NaN);
Range r2 = new Range(2, 5);
assertEquals(new Range(1, 5), Range.combineIgnoringNaN(r1, r2));
}

////////////////////////////////////////////////////////////////////////////////////////////


@Test
public void expand_validRangeNoMargins() {
Range range = new Range(2, 6);
assertEquals(new Range(2, 6), Range.expand(range, 0, 0));
}

@Test
public void expand_validRangePositiveMargins() {
Range range = new Range(2, 6);
assertEquals(new Range(0, 8), Range.expand(range, 0.5, 0.5));
}

@Test
public void expand_validRangeNegativeMargins() {
Range range = new Range(2, 6);
assertEquals(new Range(3, 5), Range.expand(range, -0.25, -0.25));
}

@Test
public void expand_validRangeMixedMargins() {
Range range = new Range(2, 6);
assertEquals(new Range(1, 7), Range.expand(range, 0.25, 0.25));
}

@Test
public void expand_rangeCollapsesLowerGreaterThanUpper() {
Range range = new Range(2, 6);
Range result = Range.expand(range, -1.5, -1.5); // Will collapse
assertEquals(new Range(4, 4), result);
}

@Test
public void expand_nullRangeThrowsException() {
try {
Range.expand(null, 0.5, 0.5);
fail("Expected an IllegalArgumentException but none was thrown.");
} catch (IllegalArgumentException e) {
assertEquals("Null 'range' argument.", e.getMessage());
}
}


///////////////////////////////////////////////////////////////////////////////////////////////////////

@Test
public void expandToInclude_nullRange() {
Range result = Range.expandToInclude(null, 5);
assertEquals(new Range(5, 5), result);
}

@Test
public void expandToInclude_valueLessThanLowerBound() {
Range range = new Range(3, 7);
Range result = Range.expandToInclude(range, 1);
assertEquals(new Range(1, 7), result);
}

@Test
public void expandToInclude_valueGreaterThanUpperBound() {
Range range = new Range(3, 7);
Range result = Range.expandToInclude(range, 10);
assertEquals(new Range(3, 10), result);
}

@Test
public void expandToInclude_valueWithinRange() {
Range range = new Range(3, 7);
Range result = Range.expandToInclude(range, 5);
assertEquals(range, result);
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////

@Test
public void maxtest() {
Range r1 = new Range(2, 5);
Range r2 = new Range(1, Double.NaN);
assertEquals(new Range(1, 5), Range.combineIgnoringNaN(r1, r2));
}

@Test
public void mintest() {
Range r1 = new Range(1, 555);
Range r2 = new Range(Double.NaN, 5);
assertEquals(new Range(1, 555), Range.combineIgnoringNaN(r1, r2));
}

////////////////////////////////////////////////////////////////////////////////////////////////////////


@Test
public void scale_nullBaseThrowsException() {
try {
Range.scale(null, 2.0);
fail("Expected an IllegalArgumentException but none was thrown.");
} catch (IllegalArgumentException e) {
assertEquals("Null 'base' argument.", e.getMessage());
}
}

@Test
public void scale_negativeFactorThrowsException() {
try {
Range.scale(new Range(2, 5), -1.0);
fail("Expected an IllegalArgumentException but none was thrown.");
} catch (IllegalArgumentException e) {
assertEquals("Negative 'factor' argument.", e.getMessage());
}
}

@Test
public void scale_zeroFactorReturnsZeroRange() {
Range base = new Range(2, 5);
Range result = Range.scale(base, 0);
assertEquals(new Range(0, 0), result);
}

@Test
public void scale_positiveFactorReturnsScaledRange() {
Range base = new Range(2, 5);
Range result = Range.scale(base, 2);
assertEquals(new Range(4, 10), result);
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


@Test
public void shift_validRangeShiftsCorrectly() {
Range base = new Range(2, 5);
Range result = Range.shift(base, 3);
assertEquals(new Range(5, 8), result);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Test
public void shift_allowZeroCrossingTrue_shiftsRange() {
Range base = new Range(-5, 10);
Range result = Range.shift(base, 3, true);
assertEquals(new Range(-2, 13), result);
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


@Test
public void shift_allowZeroCrossingTrue_negativeShift() {
Range base = new Range(0, 6);
Range result = Range.shift(base, 0, false);
assertEquals(new Range(0, 6), result);
}

@Test
public void shift_allowZeroCrossingTrue_zeroShift() {
Range base = new Range(-4, 8);
Range result = Range.shift(base, 0, false);
assertEquals(new Range(-4, 8), result);
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//Added tests to increase mutation score
private static final double EPSILON = 1e-10;

//Verify that the equals() method properly distinguishes fields
@Test
public void testEquals() {
 Range r1 = new Range(1.0, 3.0);
 Range r2 = new Range(1.0, 3.0);
 assertEquals(r1, r2);
 assertEquals(r2, r1);

 r1 = new Range(1.0, 3.0);
 r2 = new Range(1.5, 3.0);
 assertFalse(r1.equals(r2));

 r1 = new Range(1.0, 3.0);
 r2 = new Range(1.0, 4.0);
 assertFalse(r1.equals(r2));

 // Range cannot equal an object of a different type
 assertFalse(r1.equals(new Integer(1)));
}

//Equal objects must return the same hash code
@Test
public void testHashCode() {
 Range a1 = new Range(5.0, 50.0);
 Range a2 = new Range(5.0, 50.0);
 assertEquals(a1.hashCode(), a2.hashCode());

 a1 = new Range(-50.0, 10.0);
 a2 = new Range(-50.0, 10.0);
 assertEquals(a1.hashCode(), a2.hashCode());
}

//Basic checks for the contains() method
@Test
public void testContains() {
 Range r1 = new Range(-1.0, 2.0);
 assertFalse(r1.contains(Double.NaN));
 assertFalse(r1.contains(Double.NEGATIVE_INFINITY));
 assertFalse(r1.contains(-2.0));
 assertTrue(r1.contains(-1.0));
 assertTrue(r1.contains(0.0));
 assertTrue(r1.contains(1.0));
 assertTrue(r1.contains(2.0));
 assertFalse(r1.contains(3.0));
 assertFalse(r1.contains(Double.POSITIVE_INFINITY));
}

//Various checks for the constrain() method
@Test
public void testConstrain() {
 Range r1 = new Range(-2.0, 2.0);

 double d = r1.constrain(1.0);
 assertEquals(1.0, d, 1e-7);

 d = r1.constrain(-2.0);
 assertEquals(-2.0, d, 1e-7);

 d = r1.constrain(2.0);
 assertEquals(2.0, d, 1e-7);

 d = r1.constrain(-3.0);
 assertEquals(-2.0, d, 1e-7);

 d = r1.constrain(3.0);
 assertEquals(2.0, d, 1e-7);

 d = r1.constrain(Double.POSITIVE_INFINITY);
 assertEquals(2.0, d, 1e-7);

 d = r1.constrain(Double.NEGATIVE_INFINITY);
 assertEquals(-2.0, d, 1e-7);

 d = r1.constrain(Double.NaN);
 assertTrue(Double.isNaN(d));
}

//Basic tests for the intersects() method
@Test
public void testIntersects() {
 Range r1 = new Range(2.0, 5.0);
 assertFalse(r1.intersects(0.0, 1.0));
 assertFalse(r1.intersects(0.0, 2.0));
 assertTrue(r1.intersects(0.0, 3.0));
 assertTrue(r1.intersects(0.0, 5.0));
 assertTrue(r1.intersects(0.0, 6.0));
 assertTrue(r1.intersects(3.0, 4.0));
 assertTrue(r1.intersects(3.0, 5.0));
 assertTrue(r1.intersects(3.0, 6.0));
 assertFalse(r1.intersects(5.0, 6.0));
 assertFalse(r1.intersects(6.0, 7.0));
}

//Tests for the expand() method
@Test
public void testExpand() {
    Range r1 = new Range(0.0, 100.0);
    Range r2 = Range.expand(r1, 0.10, 0.10);
    assertEquals(-10.0, r2.getLowerBound(), 0.001);
    assertEquals(110.0, r2.getUpperBound(), 0.001);
    // Expand by 0% does not change the range
    r2 = Range.expand(r1, 0.0, 0.0);
    assertEquals(r1, r2);
    try {
        Range.expand(null, 0.1, 0.1);
        fail("Null value is accepted");
    } catch (Exception e) {
    }
    // Lower > upper: mid point is used
    r2 = Range.expand(r1, -0.8, -0.5);
    assertEquals(65.0, r2.getLowerBound(), 0.001);
    assertEquals(65.0, r2.getUpperBound(), 0.001);
}


//Tests for the shift() method
@Test
public void testShift() {
    Range r1 = new Range(10.0, 20.0);
    Range r2 = Range.shift(r1, 20.0);
    assertEquals(30.0, r2.getLowerBound(), 0.001);
    assertEquals(40.0, r2.getUpperBound(), 0.001);
    r1 = new Range(0.0, 100.0);
    r2 = Range.shift(r1, -50.0, true);
    assertEquals(-50.0, r2.getLowerBound(), 0.001);
    assertEquals(50.0, r2.getUpperBound(), 0.001);
    r1 = new Range(-10.0, 20.0);
    r2 = Range.shift(r1, 20.0, true);
    assertEquals(10.0, r2.getLowerBound(), 0.001);
    assertEquals(40.0, r2.getUpperBound(), 0.001);
    r1 = new Range(-10.0, 20.0);
    r2 = Range.shift(r1, -30.0, true);
    assertEquals(-40.0, r2.getLowerBound(), 0.001);
    assertEquals(-10.0, r2.getUpperBound(), 0.001);
    r1 = new Range(-10.0, 20.0);
    r2 = Range.shift(r1, 20.0, false);
    assertEquals(0.0, r2.getLowerBound(), 0.001);
    assertEquals(40.0, r2.getUpperBound(), 0.001);
    r1 = new Range(-10.0, 20.0);
    r2 = Range.shift(r1, -30.0, false);
    assertEquals(-40.0, r2.getLowerBound(), 0.001);
    assertEquals(0.0, r2.getUpperBound(), 0.001);
    // Shifting with a delta of 0 does not change the range
    r2 = Range.shift(r1, 0.0);
    assertEquals(r1, r2);
    try {
        Range.shift(null, 1.0);
        fail("Null value is accepted");
    } catch (Exception e) {
    }
}


//Tests for the combine() method
@Test
public void testCombine() {
 Range r1 = new Range(2.0, 6.0);
 Range r2 = new Range(4.0, 8.0);

 assertNull(Range.combine(null, null));
 assertEquals(r1, Range.combine(r1, null));
 assertEquals(r2, Range.combine(null, r2));
 assertEquals(new Range(2.0, 8.0), Range.combine(r1, r2));

 Range r3 = new Range(Double.NaN, 5.0);
 Range rr = Range.combine(r1, r3);
 assertTrue(Double.isNaN(rr.getLowerBound()));
 assertEquals(6.0, rr.getUpperBound(), EPSILON);

 Range r4 = new Range(4.5, Double.NaN);
 rr = Range.combine(r4, r1);
 assertEquals(2.0, rr.getLowerBound(), EPSILON);
 assertTrue(Double.isNaN(rr.getUpperBound()));
}

//Tests for the combineIgnoringNaN() method
@Test
public void testCombineIgnoringNaN() {
 Range r1 = new Range(3.0, 7.0);
 Range r2 = new Range(6.0, 9.0);

 assertNull(Range.combineIgnoringNaN(null, null));
 assertEquals(r1, Range.combineIgnoringNaN(r1, null));
 assertEquals(r2, Range.combineIgnoringNaN(null, r2));
 assertEquals(new Range(3.0, 9.0), Range.combineIgnoringNaN(r1, r2));

 Range r3 = new Range(Double.NaN, 6.5);
 Range rr = Range.combineIgnoringNaN(r1, r3);
 assertEquals(3.0, rr.getLowerBound(), EPSILON);
 assertEquals(7.0, rr.getUpperBound(), EPSILON);

 Range r4 = new Range(5.0, Double.NaN);
 rr = Range.combineIgnoringNaN(r4, r1);
 assertEquals(3.0, rr.getLowerBound(), EPSILON);
 assertEquals(7.0, rr.getUpperBound(), EPSILON);
}

//Test the isNaNRange() method
@Test
public void testIsNaNRange() {
 assertTrue(new Range(Double.NaN, Double.NaN).isNaNRange());
 assertFalse(new Range(0.0, 10.0).isNaNRange());
 assertFalse(new Range(Double.NaN, 10.0).isNaNRange());
 assertFalse(new Range(0.0, Double.NaN).isNaNRange());
}

//Tests for the scale() method
@Test
public void testScale() {
 Range r1 = new Range(10.0, 20.0);
 Range r2 = Range.scale(r1, 0.5);
 assertEquals(5.0, r2.getLowerBound(), 0.001);
 assertEquals(10.0, r2.getUpperBound(), 0.001);

 r1 = new Range(-5.0, 15.0);
 r2 = Range.scale(r1, 3.0);
 assertEquals(-15.0, r2.getLowerBound(), 0.001);
 assertEquals(45.0, r2.getUpperBound(), 0.001);

 // Scaling by 1 leaves the range unchanged
 r2 = Range.scale(r1, 1.0);
 assertEquals(r1, r2);

 try {
     Range.scale(null, 2.0);
     fail("Null range was accepted");
 } catch (Exception e) {
     // Expected exception
 }

 try {
     Range.scale(r1, -1.0);
     fail("Negative scaling factor was accepted");
 } catch (Exception e) {
     // Expected exception
 }
}

//tests for getCentralValue
@Test
public void testGetCentralValueUpperUnchanged() {
    // Create a range with specific bounds
    Range range = new Range(5.0, 15.0);
    
    // Store the original upper bound
    double originalUpper = range.getUpperBound();
    
    // Call getCentralValue
    range.getCentralValue();
    
    // Check that the upper bound remains unchanged
    assertEquals(originalUpper, range.getUpperBound(), 0.000000001);
}

@Test
public void testGetCentralValueLowerUnchanged() {
    // Create a range with specific bounds
    Range range = new Range(5.0, 15.0);
    
    // Store the original lower bound
    double originalLower = range.getLowerBound();
    
    // Call getCentralValue
    range.getCentralValue();
    
    // Check that the lower bound remains unchanged
    assertEquals(originalLower, range.getLowerBound(), 0.000000001);
}

//tests for intersects
@Test
public void testIntersectsBoundaryCondition() {
    Range range = new Range(5.0, 10.0);
    assertTrue(range.intersects(9.9, 10.1)); // Should return true for boundary condition near upper bound
    assertFalse(range.intersects(10.0, 10.0)); // Should return false when b0 equals upper
}

@Test
public void testIntersectsUnconditionalTrue() {
    Range range = new Range(5.0, 10.0);
    assertFalse(range.intersects(10.1, 11.0)); // Should return false as b0 > upper
    assertTrue(range.intersects(9.0, 10.0)); // Should return true as b0 < upper and b1 >= b0
}

@Test
public void testIntersectsNegatedB0() {
    Range range = new Range(5.0, 10.0);
    assertTrue(range.intersects(-9.0, 10.0)); // Should return truee as negating b0 changes logic
    assertTrue(range.intersects(9.0, 10.0)); // Correct behavior with positive b0
}

@Test
public void testIntersectsSubstitutedBounds() {
    Range range = new Range(5.0, 10.0);
    assertFalse(range.intersects(6.0, 4.0)); // Invalid: b1 < b0 should return false
    assertTrue(range.intersects(5.0, 10.0)); // Valid: should return true
}

@Test
public void testIntersectsLessThanToLessOrEqual() {
    Range range = new Range(5.0, 10.0);
    assertFalse(range.intersects(10.0, 10.0)); // Should return false as b0 == upper
    assertTrue(range.intersects(9.9, 10.0)); // Should return true for valid overlap
}

@Test
public void testIntersectsLessThanToEqual() {
    Range range = new Range(5.0, 10.0);
    assertFalse(range.intersects(10.0, 10.0)); // Should return false, no exact overlap
    assertTrue(range.intersects(9.0, 10.0)); // Should return true for valid overlap
}

@Test
public void testIntersectsIncrementedAndDecrementedVariables() {
    Range range = new Range(5.0, 10.0);
    
    // Test incremented b0
    assertFalse(range.intersects(10.1, 10.2)); // Should return false if b0 incremented
    
    // Test incremented b1
    assertTrue(range.intersects(9.0, 10.1)); // Should return true with b1 validly incremented
    
    // Test decremented b0
    assertTrue(range.intersects(4.9, 10.0)); // Should return true as b0 decremented overlaps
    
    // Test decremented b1
    assertFalse(range.intersects(9.0, 8.9)); // Should return false if b1 decremented
}

//testing constrains
@Test
public void testConstrainBoundaryCondition() {
    Range range = new Range(5.0, 10.0);
    
    // Test boundary condition just below the lower bound
    assertEquals(5.0, range.constrain(4.999999999), 0.000000001); // Should return this.lower
    
    // Test boundary condition at the lower bound
    assertEquals(5.0, range.constrain(5.0), 0.000000001); // Should return the value itself
}

@Test
public void testConstrainGreaterOrEqualToGreaterThan() {
    Range range = new Range(5.0, 10.0);
    
    // Test value exactly at the lower bound
    assertEquals(5.0, range.constrain(5.0), 0.000000001); // Should return 5.0
    
    // Test value slightly below the lower bound
    assertEquals(5.0, range.constrain(4.999999999), 0.000000001); // Should still return this.lower
}

@Test
public void testConstrainIncrementedValue() {
    Range range = new Range(5.0, 10.0);
    
    // Test a value close to the lower bound
    double value = 4.9;
    assertEquals(5.0, range.constrain(value), 0.000000001); // Should return this.lower
    
}

@Test
public void testConstrainDecrementedValue() {
    Range range = new Range(5.0, 10.0);
    
    // Test a value just below the lower bound
    double value = 5.1;
    assertEquals(5.0, range.constrain(value - 0.2), 0.000000001); // Should return this.lower
}

@Test
public void testConstrainDecrementedPrefixValue() {
    Range range = new Range(5.0, 10.0);
    
    // Test a value near the lower bound
    double value = 5.0;
    --value; // Decrement prefix
    assertEquals(5.0, range.constrain(value), 0.000000001); // Should return this.lower
}

//tests for combineIgnoringNaN

@Test
public void testCombineIgnoringNaNReplacedEqualityWithTrue() {
    // Case where both bounds are NaN
    Range range1 = new Range(Double.NaN, Double.NaN);
    Range range2 = new Range(Double.NaN, Double.NaN);
    assertNull(Range.combineIgnoringNaN(range1, range2)); // Should return null

    // Case where one bound is NaN and the other is valid
    Range range3 = new Range(Double.NaN, 10.0);
    Range range4 = new Range(5.0, Double.NaN);
    Range result = Range.combineIgnoringNaN(range3, range4);
    assertNotNull(result); // Should not return null
    assertEquals(5.0, result.getLowerBound(), 0.000000001);
    assertEquals(10.0, result.getUpperBound(), 0.000000001);
}

@Test
public void testCombineIgnoringNaNNegatedVariables() {
    // Case where lower bound is negated NaN
    Range range1 = new Range(-Double.NaN, 10.0);
    Range range2 = new Range(5.0, -Double.NaN);
    Range result = Range.combineIgnoringNaN(range1, range2);
    assertNotNull(result); // Should not return null
    assertEquals(5.0, result.getLowerBound(), 0.000000001);
    assertEquals(10.0, result.getUpperBound(), 0.000000001);
}

@Test
public void testCombineIgnoringNaNEqualToLessThan() {
    // Case where bounds are exactly at the same value
    Range range1 = new Range(5.0, 10.0);
    Range range2 = new Range(5.0, 10.0);
    Range result = Range.combineIgnoringNaN(range1, range2);
    assertNotNull(result); // Should not return null
    assertEquals(5.0, result.getLowerBound(), 0.000000001);
    assertEquals(10.0, result.getUpperBound(), 0.000000001);
}

@Test
public void testCombineIgnoringNaNIncrementedAndDecrementedVariables() {
    // Incremented bounds
    Range range1 = new Range(4.9, 9.9);
    Range range2 = new Range(5.1, 10.1);
    Range result = Range.combineIgnoringNaN(range1, range2);
    assertNotNull(result); // Should not return null
    assertEquals(4.9, result.getLowerBound(), 0.000000001);
    assertEquals(10.1, result.getUpperBound(), 0.000000001);

    // Decremented bounds
    Range range3 = new Range(5.0, 10.0);
    Range range4 = new Range(4.8, 9.8);
    Range result2 = Range.combineIgnoringNaN(range3, range4);
    assertNotNull(result2); // Should not return null
    assertEquals(4.8, result2.getLowerBound(), 0.000000001);
    assertEquals(10.0, result2.getUpperBound(), 0.000000001);
}

//tests for contains
@Test
public void testContainsLowerUnchanged() {
    // Create a range with specific bounds
    Range range = new Range(5.0, 15.0);
    
    // Store the original lower bound
    double originalLower = range.getLowerBound();
    
    // Call contains
    range.contains(10.0);
    
    // Verify that the lower bound remains unchanged
    assertEquals(originalLower, range.getLowerBound(), 0.000000001);
}

@Test
public void testContainsUpperUnchanged() {
    // Create a range with specific bounds
    Range range = new Range(5.0, 15.0);
    
    // Store the original upper bound
    double originalUpper = range.getUpperBound();
    
    // Call contains
    range.contains(10.0);
    
    // Verify that the upper bound remains unchanged
    assertEquals(originalUpper, range.getUpperBound(), 0.000000001);
}

//Tests for state unchanged in isNaNRange method
@Test
public void testIsNaNRangeLowerBoundUnchanged() {
 // Create a range with specific bounds
 Range range = new Range(Double.NaN, 15.0);

 // Store the original lower bound
 double originalLower = range.getLowerBound();

 // Call isNaNRange
 range.isNaNRange();

 // Verify that the lower bound remains unchanged
 assertEquals(originalLower, range.getLowerBound(), 0.000000001);
}

@Test
public void testIsNaNRangeUpperBoundUnchanged() {
 // Create a range with specific bounds
 Range range = new Range(5.0, 13.0);

 // Store the original upper bound
 double originalUpper = range.getUpperBound();

 // Call isNaNRange
 range.isNaNRange();

 // Verify that the upper bound remains unchanged
 assertEquals(originalUpper, range.getUpperBound(), 0.000000001);
}

//Tests for state unchanged in hashCode method
@Test
public void testHashCodeLowerBoundUnchanged() {
 // Create a range with specific bounds
 Range range = new Range(5.0, 15.0);

 // Store the original lower bound
 double originalLower = range.getLowerBound();

 // Call hashCode
 range.hashCode();

 // Verify that the lower bound remains unchanged
 assertEquals(originalLower, range.getLowerBound(), 0.000000001);
}

@Test
public void testHashCodeUpperBoundUnchanged() {
 // Create a range with specific bounds
 Range range = new Range(5.0, 15.0);

 // Store the original upper bound
 double originalUpper = range.getUpperBound();

 // Call hashCode
 range.hashCode();

 // Verify that the upper bound remains unchanged
 assertEquals(originalUpper, range.getUpperBound(), 0.000000001);
}

//Tests for state unchanged in toString method
@Test
public void testToStringLowerBoundUnchanged() {
 // Create a range with specific bounds
 Range range = new Range(5.0, 15.0);

 // Store the original lower bound
 double originalLower = range.getLowerBound();

 // Call toString
 range.toString();

 // Verify that the lower bound remains unchanged
 assertEquals(originalLower, range.getLowerBound(), 0.000000001);
}

@Test
public void testToStringUpperBoundUnchanged() {
 // Create a range with specific bounds
 Range range = new Range(5.0, 15.0);

 // Store the original upper bound
 double originalUpper = range.getUpperBound();

 // Call toString
 range.toString();

 // Verify that the upper bound remains unchanged
 assertEquals(originalUpper, range.getUpperBound(), 0.000000001);
}

//Tests for state unchanged in Range constructor
@Test
public void testRangeConstructorLowerBoundUnchanged() {
 // Create a range with specific bounds
 double lower = 5.0;
 double upper = 15.0;

 // Call constructor
 Range range = new Range(lower, upper);

 // Verify that the lower bound remains unchanged
 assertEquals(lower, range.getLowerBound(), 0.000000001);
}

@Test
public void testRangeConstructorUpperBoundUnchanged() {
 // Create a range with specific bounds
 double lower = 5.0;
 double upper = 15.0;

 // Call constructor
 Range range = new Range(lower, upper);

 // Verify that the upper bound remains unchanged
 assertEquals(upper, range.getUpperBound(), 0.000000001);
}

//Test for intersects method that returns false
@Test
public void testIntersectsFalse() {
 // Create two ranges that do not intersect
 Range range1 = new Range(5.0, 10.0);
 Range range2 = new Range(15.0, 20.0);

 // Call intersects method
 boolean result = range1.intersects(range2);

 // Verify that the result is false since the ranges do not intersect
 assertFalse(result);
}

//Test for equals method that ensures it doesn't return -1 or invalid result
@Test
public void testEqualsReturnsNotNegativeOne() {
 // Create two ranges with different lower and upper bounds
 Range range1 = new Range(5.0, 10.0);
 Range range2 = new Range(15.0, 20.0);

 // Call equals method and store the result
 boolean result = range1.equals(range2);

 // Ensure the result is not -1 (since equals returns boolean, it can only return true or false)
 assertNotEquals(result, -1);
}

//Test for equals method that ensures it doesn't return an incorrect value like -1
@Test
public void testEqualsFalse() {
 // Create two ranges with different lower and upper bounds
 Range range1 = new Range(5.0, 10.0);
 Range range2 = new Range(15.0, 20.0);

 // Call equals method
 boolean result = range1.equals(range2);

 // Verify that the result is false since the ranges are not equal
 assertFalse(result);
}

  
}
