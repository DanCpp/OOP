package org.example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a student's electronic gradebook.
 * Stores the history of assessments and provides methods to analyze academic performance.
 */
public class Gradebook {

    /**
     * Enumeration of possible marks.
     */
    public enum Mark {
        EXCELLENT(5),
        GOOD(4),
        SATISFACTORY(3),
        CREDIT(0),
        NO_CREDIT(0);

        private final int numericValue;

        Mark(int numericValue) {
            this.numericValue = numericValue;
        }

        public int getNumericValue() {
            return numericValue;
        }

        public boolean isNumeric() {
            return numericValue > 0;
        }
    }

    /**
     * Types of academic assessments.
     */
    public enum ControlType {
        EXAM, DIFF_CREDIT, CREDIT, VKR_DEFENSE, OTHER
    }

    /**
     * Record of passing a specific subject.
     *
     * @param semester semester number
     * @param subject  subject name
     * @param type     assessment type
     * @param mark     received mark
     */
    public record Assessment(int semester, String subject, ControlType type, Mark mark) {
        public Assessment {
            if (semester < 1 || semester > 8) {
                throw new IllegalArgumentException("");
            }
        }
    }

    private final List<Assessment> assessments = new ArrayList<>();

    /**
     * Adds a new assessment record to the gradebook.
     *
     * @param assessment the assessment record object
     */
    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    /**
     * Calculates the current average grade for the entire period of study.
     * Only differentiated marks (exams, diff credits, thesis) are considered.
     *
     * @return the average grade, or 0.0 if there are no suitable marks
     */
    public double calculateAverageGrade() {
        return assessments.stream()
                .filter(a -> a.mark().isNumeric())
                .mapToInt(a -> a.mark().getNumericValue())
                .average()
                .orElse(0.0);
    }

    /**
     * Checks eligibility for transfer from paid to state-funded (budget) education.
     * Requires no satisfactory marks for exams and no unpassed credits in the last two semesters.
     *
     * @return true if the student meets the transfer criteria
     */
    public boolean canTransferToBudget() {
        if (assessments.isEmpty()) {
            return false;
        }

        int currentSemester = assessments.stream()
                .mapToInt(Assessment::semester)
                .max()
                .orElse(1);

        List<Assessment> lastTwoSemesters = assessments.stream()
                .filter(a -> a.semester() >= currentSemester - 1)
                .toList();

        boolean hasSatisfactoryExams = lastTwoSemesters.stream()
                .anyMatch(a -> a.type() == ControlType.EXAM && a.mark() == Mark.SATISFACTORY);

        boolean hasNoCredits = lastTwoSemesters.stream()
                .anyMatch(a -> a.type() == ControlType.CREDIT && a.mark() == Mark.NO_CREDIT);

        return !hasSatisfactoryExams && !hasNoCredits;
    }

    /**
     * Checks eligibility for a diploma with honors (red diploma).
     * Requires 75% excellent final marks, no satisfactory marks, all credits passed,
     * and an excellent thesis defense.
     *
     * @return true if the current academic performance qualifies for a red diploma
     */
    public boolean isEligibleForRedDiploma() {
        if (assessments.isEmpty()) {
            return true;
        }

        boolean hasSatisfactory = assessments.stream()
                .anyMatch(a -> a.mark() == Mark.SATISFACTORY);
        if (hasSatisfactory) {
            return false;
        }

        boolean hasNoCredits = assessments.stream()
                .anyMatch(a -> a.type() == ControlType.CREDIT && a.mark() == Mark.NO_CREDIT);
        if (hasNoCredits) {
            return false;
        }

        Optional<Assessment> vkr = assessments.stream()
                .filter(a -> a.type() == ControlType.VKR_DEFENSE)
                .findFirst();

        if (vkr.isPresent() && vkr.get().mark() != Mark.EXCELLENT) {
            return false;
        }

        Collection<Assessment> finalGrades = assessments.stream()
                .filter(a -> a.mark().isNumeric() && a.type() != ControlType.VKR_DEFENSE)
                .collect(Collectors.toMap(
                        Assessment::subject,
                        a -> a,
                        (a1, a2) -> a1.semester() > a2.semester() ? a1 : a2
                ))
                .values();

        if (finalGrades.isEmpty()) {
            return true;
        }

        long excellentCount = finalGrades.stream()
                .filter(a -> a.mark() == Mark.EXCELLENT)
                .count();

        double excellentPercentage = (double) excellentCount / finalGrades.size();

        return excellentPercentage >= 0.75;
    }

    /**
     * Checks eligibility for an increased scholarship in the current semester.
     * Requires only excellent marks in the last completed semester.
     *
     * @return true if the student is eligible for an increased scholarship
     */
    public boolean isEligibleForIncreasedScholarship() {
        if (assessments.isEmpty()) {
            return false;
        }

        int currentSemester = assessments.stream()
                .mapToInt(Assessment::semester)
                .max()
                .orElse(1);

        List<Assessment> lastSemesterAssessments = assessments.stream()
                .filter(a -> a.semester() == currentSemester)
                .toList();

        if (lastSemesterAssessments.isEmpty()) {
            return false;
        }

        return lastSemesterAssessments.stream()
                .filter(a -> a.mark().isNumeric())
                .allMatch(a -> a.mark() == Mark.EXCELLENT);
    }
}