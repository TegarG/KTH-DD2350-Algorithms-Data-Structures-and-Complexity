
import java.util.ArrayList;

class Main{
    static int n, s, k;
    static ArrayList<Integer>[] canBePlayedBy;

    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);

        n = io.getInt();
        s = io.getInt();
        k = io.getInt();

        // Roller kan spelas av
        // Index i är r_i, och listan innehåller alla möjliga skådespelare
        canBePlayedBy = (ArrayList<Integer>[]) new ArrayList[n+1];
        for (int i = 0; i < canBePlayedBy.length; i++) {
            canBePlayedBy[i] = new ArrayList<>();
        }
        for (int i = 1; i <= n; i++){
            int actorCount = io.getInt();
            for (int j = 0; j < actorCount; j++){
                canBePlayedBy[i].add(io.getInt());
            }
        }

        // Scener
        // Index [i][j] representerar r_i och r_j, true om de är i samma scen.
        boolean[][] sceneConflict = new boolean[n+1][n+1];
        for (int i = 0; i < s; i++){
            int roleCount = io.getInt();
            int[] roles = new int[roleCount];
            // Samla roller
            for (int j = 0; j < roleCount; j++){
                roles[j] = io.getInt();
            }
            // Lagra scen konflikter
            for (int j = 0; j < roleCount; j++){
                for (int l = j+1; l < roleCount; l++){
                    sceneConflict[roles[j]][roles[l]] = true;
                    sceneConflict[roles[l]][roles[j]] = true;
                }
            }
        }

        // Tilldelning
        // Index i är p_i, listan är alla roller som p_i spelar
        // Det finns k skådespelare + n-1 möjliga superskådisar
        ArrayList<Integer>[] roleAssignment = (ArrayList<Integer>[]) new ArrayList[k+n];
        for (int i = 0; i < roleAssignment.length; i++) {
            roleAssignment[i] = new ArrayList<>();
        }

        int p1RoleAssignment = 0;
        int p2RoleAssignment = 0;
        boolean assignmentFound = false;
        
        // Tilldela roll till p1, p2 vardera så att inga konflikter uppstår
        for (int roleP1: possibleRolesList(1)){
            for (int roleP2: possibleRolesList(2)){
                if (roleP1 != roleP2 && !sceneConflict[roleP1][roleP2]){
                    p1RoleAssignment = roleP1;
                    p2RoleAssignment = roleP2;
                    assignmentFound = true;
                    break;
                }
            }
            if (assignmentFound) break;
        }

        int actorsUsed = tryAssigningRoles(p1RoleAssignment, p2RoleAssignment, sceneConflict, roleAssignment);

        // Output
        io.println(actorsUsed);

        for (int i = 1; i < roleAssignment.length; i++){
            if (!roleAssignment[i].isEmpty()){
                io.print((i) + " " + roleAssignment[i].size());
                for (int r: roleAssignment[i]) io.print(" " + r);
                io.println();
            }
        }
        io.close();
    }
    
    static ArrayList<Integer> possibleRolesList(int actor){
        ArrayList<Integer> possibleRoles = new ArrayList<>();
        for (int r = 1; r <= n; r++) {
            for (int a: canBePlayedBy[r]){
                if (a == actor) possibleRoles.add(r);
            }
        }
        return possibleRoles;
    }

    static int tryAssigningRoles(int roleP1, int roleP2, boolean[][] sceneConflict, ArrayList<Integer>[] roleAssignment){

        // Markera om roll är tilldelad eller ej
        boolean[] assigned = new boolean[n+1];

        // Tilldela roller till p1 och p2
        roleAssignment[1].add(roleP1);
        roleAssignment[2].add(roleP2);
        assigned[roleP1] = true;
        assigned[roleP2] = true;

        // Tilldela resterande roller till resterande skådespelare
        // Superskådisar börjar på k+1
        int superActor = k + 1;
        for (int r = 1; r <= n; r++) {
            if (assigned[r]) continue;
            boolean roleAssigned = false;

            // Försök tilldela vanlig skådespelare
            for (int actor: canBePlayedBy[r]) {
                if (actor == 1 || actor == 2) continue;
                if (roleAssigned) break;

                // Kolla om scen konflikter finns
                boolean noConflicts = true;                    
                for (int assignedRole: roleAssignment[actor]){
                    if (sceneConflict[assignedRole][r]){
                        noConflicts = false;
                        break;
                    }
                }
                if (noConflicts){
                    roleAssignment[actor].add(r);
                    roleAssigned = true;
                }
            }

            // Om ingen roll kan tilldelas -> tilldela superskådis
            if (!roleAssigned){
                roleAssignment[superActor++].add(r);
            }
        }

        // Räkna antalet använda skådespelare
        int assignedActorsCount = 0;
        for (int i = 1; i < roleAssignment.length; i++){
            if (!roleAssignment[i].isEmpty()) assignedActorsCount++;
        }

        return assignedActorsCount;
    }
}

