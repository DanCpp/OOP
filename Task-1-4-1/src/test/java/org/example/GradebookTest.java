package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class GradebookTest {

    private Gradebook gradebook;

    @BeforeEach
    void setUp() {
        gradebook = new Gradebook();
    }

    @Test
    void testCalculateAverageGrade_Empty() {
        assertEquals(0.0, gradebook.calculateAverageGrade(), 0.001);
    }

    @Test
    void testCalculateAverageGrade_MixedMarks() {
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT)); // 5
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Physics", Gradebook.ControlType.EXAM, Gradebook.Mark.GOOD)); // 4
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "History", Gradebook.ControlType.CREDIT, Gradebook.Mark.CREDIT)); // Ignored

        assertEquals(4.5, gradebook.calculateAverageGrade(), 0.001);
    }

    @Test
    void testCanTransferToBudget_Empty() {
        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget_Success() {
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.SATISFACTORY)); // Older semester, ignored
        gradebook.addAssessment(new Gradebook.Assessment(2,
                "Physics", Gradebook.ControlType.EXAM, Gradebook.Mark.GOOD));
        gradebook.addAssessment(new Gradebook.Assessment(3,
                "Chemistry", Gradebook.ControlType.DIFF_CREDIT, Gradebook.Mark.SATISFACTORY)); // Diff credit '3' is allowed

        assertTrue(gradebook.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget_FailsDueToRecentSatisfactoryExam() {
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.GOOD));
        gradebook.addAssessment(new Gradebook.Assessment(2,
                "Physics", Gradebook.ControlType.EXAM, Gradebook.Mark.SATISFACTORY)); // Fails here (last 2 semesters)

        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget_FailsDueToNoCredit() {
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Math", Gradebook.ControlType.CREDIT, Gradebook.Mark.NO_CREDIT));

        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testIsEligibleForRedDiploma_Empty() {
        assertFalse(gradebook.isEligibleForRedDiploma());
    }

    @Test
    void testIsEligibleForRedDiploma_Success() {
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT));
        gradebook.addAssessment(new Gradebook.Assessment(2,
                "Physics", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT));
        gradebook.addAssessment(new Gradebook.Assessment(3,
                "Chemistry", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT));
        gradebook.addAssessment(new Gradebook.Assessment(4,
                "Biology", Gradebook.ControlType.EXAM, Gradebook.Mark.GOOD)); // 75% Excellent
        gradebook.addAssessment(new Gradebook.Assessment(8,
                "Thesis", Gradebook.ControlType.VKR_DEFENSE, Gradebook.Mark.EXCELLENT));

        assertTrue(gradebook.isEligibleForRedDiploma());
    }

    @Test
    void testIsEligibleForRedDiploma_FailsDueToPercentage() {
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT));
        gradebook.addAssessment(new Gradebook.Assessment(2,
                "Physics", Gradebook.ControlType.EXAM, Gradebook.Mark.GOOD));
        gradebook.addAssessment(new Gradebook.Assessment(3,
                "Chemistry", Gradebook.ControlType.EXAM, Gradebook.Mark.GOOD)); // Only 33% Excellent

        assertFalse(gradebook.isEligibleForRedDiploma());
    }

    @Test
    void testIsEligibleForRedDiploma_FailsDueToSatisfactory() {
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT));
        gradebook.addAssessment(new Gradebook.Assessment(2,
                "Physics", Gradebook.ControlType.EXAM, Gradebook.Mark.SATISFACTORY)); // Any '3' ruins it

        assertFalse(gradebook.isEligibleForRedDiploma());
    }

    @Test
    void testIsEligibleForRedDiploma_FailsDueToVKR() {
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT));
        gradebook.addAssessment(new Gradebook.Assessment(8,
                "Thesis", Gradebook.ControlType.VKR_DEFENSE, Gradebook.Mark.GOOD));

        assertFalse(gradebook.isEligibleForRedDiploma());
    }

    @Test
    void testIsEligibleForRedDiploma_TakesLastGradeIntoAccount() {
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.GOOD));
        gradebook.addAssessment(new Gradebook.Assessment(2,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT)); // Overwrites Math with 5
        gradebook.addAssessment(new Gradebook.Assessment(3,
                "Physics", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT)); // Total: 2 subjects, both 5 (100%)

        assertTrue(gradebook.isEligibleForRedDiploma());
    }

    @Test
    void testIsEligibleForIncreasedScholarship_Empty() {
        assertFalse(gradebook.isEligibleForIncreasedScholarship());
    }

    @Test
    void testIsEligibleForIncreasedScholarship_Success() {
        gradebook.addAssessment(new Gradebook.Assessment(1,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.GOOD));
        // Current semester is 2
        gradebook.addAssessment(new Gradebook.Assessment(2,
                "Physics", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT));
        gradebook.addAssessment(new Gradebook.Assessment(2,
                "History", Gradebook.ControlType.CREDIT, Gradebook.Mark.CREDIT));

        assertTrue(gradebook.isEligibleForIncreasedScholarship());
    }

    @Test
    void testIsEligibleForIncreasedScholarship_FailsDueToGoodMark() {
        gradebook.addAssessment(new Gradebook.Assessment(2,
                "Physics", Gradebook.ControlType.EXAM, Gradebook.Mark.EXCELLENT));
        gradebook.addAssessment(new Gradebook.Assessment(2,
                "Math", Gradebook.ControlType.EXAM, Gradebook.Mark.GOOD));

        assertFalse(gradebook.isEligibleForIncreasedScholarship());
    }
}