import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Vote {
    private String voterId;
    private String candidate;
    private String hash;

    public Vote(String voterId, String candidate) {
        this.voterId = voterId;
        this.candidate = candidate;
        this.hash = generateHash(voterId + candidate);
    }

    private String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHash() {
        return hash;
    }

    public String getCandidate() {
        return candidate;
    }
}

class VotingSystem {
    private List<Vote> votes;

    public VotingSystem() {
        votes = new ArrayList<>();
    }

    public void castVote(String voterId, String candidate) {
        Vote vote = new Vote(voterId, candidate);
        votes.add(vote);
        System.out.println("Vote cast successfully!");
    }

    public void tallyVotes() {
        int candidateACount = 0;
        int candidateBCount = 0;

        for (Vote vote : votes) {
            if (vote.getCandidate().equals("Candidate A")) {
                candidateACount++;
            } else if (vote.getCandidate().equals("Candidate B")) {
                candidateBCount++;
            }
        }

        System.out.println("Total votes for Candidate A: " + candidateACount);
        System.out.println("Total votes for Candidate B: " + candidateBCount);
    }
}

public class ElectronicVoting {
    public static void main(String[] args) {
        VotingSystem votingSystem = new VotingSystem();
        Scanner scanner = new Scanner(System.in);
        String continueVoting;

        do {
            System.out.print("Enter your voter ID: ");
            String voterId = scanner.nextLine();
            System.out.print("Enter your candidate (Candidate A or Candidate B): ");
            String candidate = scanner.nextLine();
            votingSystem.castVote(voterId, candidate);

            System.out.print("Do you want to cast another vote? (yes/no): ");
            continueVoting = scanner.nextLine();
        } while (continueVoting.equalsIgnoreCase("yes"));

        votingSystem.tallyVotes();
        scanner.close();
    }
}
