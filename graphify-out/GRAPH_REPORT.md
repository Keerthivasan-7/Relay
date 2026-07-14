# Graph Report - Relay  (2026-06-28)

## Corpus Check
- 26 files · ~6,802 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 91 nodes · 109 edges · 19 communities (14 shown, 5 thin omitted)
- Extraction: 82% EXTRACTED · 18% INFERRED · 0% AMBIGUOUS · INFERRED: 20 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- [[_COMMUNITY_Community 0|Community 0]]
- [[_COMMUNITY_Community 1|Community 1]]
- [[_COMMUNITY_Community 2|Community 2]]
- [[_COMMUNITY_Community 3|Community 3]]
- [[_COMMUNITY_Community 4|Community 4]]
- [[_COMMUNITY_Community 5|Community 5]]
- [[_COMMUNITY_Community 6|Community 6]]
- [[_COMMUNITY_Community 7|Community 7]]
- [[_COMMUNITY_Community 8|Community 8]]
- [[_COMMUNITY_Community 9|Community 9]]
- [[_COMMUNITY_Community 10|Community 10]]
- [[_COMMUNITY_Community 11|Community 11]]
- [[_COMMUNITY_Community 12|Community 12]]
- [[_COMMUNITY_Community 15|Community 15]]

## God Nodes (most connected - your core abstractions)
1. `HomeScreen()` - 9 edges
2. `FloatingNavBar()` - 7 edges
3. `CallLogsPermission()` - 7 edges
4. `ContactsPermission()` - 7 edges
5. `ContactsScreen()` - 6 edges
6. `RecentsScreen()` - 6 edges
7. `RelaySearchBar()` - 5 edges
8. `FavoritesScreen()` - 5 edges
9. `ContactFetcher` - 5 edges
10. `openAppSettings()` - 4 edges

## Surprising Connections (you probably didn't know these)
- `ContactsPermission()` --calls--> `checkContactsPermission()`  [INFERRED]
  app/src/main/java/com/keerthi/relay/utils/permissionhelper/ContactsPermission.kt → app/src/main/java/com/keerthi/relay/state/PermissionState.kt
- `CallLogsPermission()` --calls--> `checkCallLogsPermission()`  [INFERRED]
  app/src/main/java/com/keerthi/relay/utils/permissionhelper/CallLogsPermission.kt → app/src/main/java/com/keerthi/relay/state/PermissionState.kt
- `HomeScreen()` --calls--> `FloatingNavBar()`  [INFERRED]
  app/src/main/java/com/keerthi/relay/ui/screens/home/RecentsScreen.kt → app/src/main/java/com/keerthi/relay/ui/components/FloatingBottomBar.kt
- `HomeScreen()` --calls--> `RatingBottomSheet()`  [INFERRED]
  app/src/main/java/com/keerthi/relay/ui/screens/home/RecentsScreen.kt → app/src/main/java/com/keerthi/relay/ui/components/RatingSheet.kt
- `ContactsScreen()` --calls--> `RelaySearchBar()`  [INFERRED]
  app/src/main/java/com/keerthi/relay/ui/screens/home/ContactsScreen.kt → app/src/main/java/com/keerthi/relay/ui/components/SearchBars.kt

## Import Cycles
- None detected.

## Communities (19 total, 5 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.25
Nodes (9): Modifier, String, CallLogEntry, Modifier, RelaySearchBar(), TopBar(), CallLogItem(), HomeScreen() (+1 more)

### Community 1 - "Community 1"
Cohesion: 0.25
Nodes (5): Boolean, Bundle, ComponentActivity, MainActivity, RelayTheme()

### Community 2 - "Community 2"
Cohesion: 0.53
Nodes (5): Activity, CallLogsPermissionState, checkCallLogsPermission(), checkContactsPermission(), ContactPermissionState

### Community 3 - "Community 3"
Cohesion: 0.39
Nodes (7): Boolean, Modifier, FloatingNavBar(), NavBarItem(), NavItem, Preview(), Int

### Community 4 - "Community 4"
Cohesion: 0.15
Nodes (10): Context, String, String, Modifier, Modifier, MyButton(), PermissionSettingsSheet(), openAppSettings() (+2 more)

### Community 5 - "Community 5"
Cohesion: 0.38
Nodes (5): CallLogEntry, Contact, Context, List, ContactFetcher

### Community 6 - "Community 6"
Cohesion: 0.50
Nodes (4): Modifier, String, DialButton(), DialScreen()

### Community 7 - "Community 7"
Cohesion: 0.60
Nodes (4): Contact, Modifier, FavoriteItem(), FavoritesScreen()

### Community 15 - "Community 15"
Cohesion: 0.50
Nodes (4): Contact, Modifier, ContactItem(), ContactsScreen()

## Knowledge Gaps
- **20 isolated node(s):** `Bundle`, `Context`, `CallLogEntry`, `Contact`, `String` (+15 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `HomeScreen()` connect `Community 0` to `Community 1`, `Community 3`, `Community 6`, `Community 7`, `Community 11`, `Community 15`?**
  _High betweenness centrality (0.358) - this node is a cross-community bridge._
- **Why does `ContactsScreen()` connect `Community 15` to `Community 0`, `Community 4`?**
  _High betweenness centrality (0.164) - this node is a cross-community bridge._
- **Why does `RecentsScreen()` connect `Community 0` to `Community 4`?**
  _High betweenness centrality (0.144) - this node is a cross-community bridge._
- **Are the 7 inferred relationships involving `HomeScreen()` (e.g. with `FloatingNavBar()` and `RatingBottomSheet()`) actually correct?**
  _`HomeScreen()` has 7 INFERRED edges - model-reasoned connections that need verification._
- **Are the 5 inferred relationships involving `CallLogsPermission()` (e.g. with `RecentsScreen()` and `MyButton()`) actually correct?**
  _`CallLogsPermission()` has 5 INFERRED edges - model-reasoned connections that need verification._
- **Are the 5 inferred relationships involving `ContactsPermission()` (e.g. with `ContactsScreen()` and `MyButton()`) actually correct?**
  _`ContactsPermission()` has 5 INFERRED edges - model-reasoned connections that need verification._
- **Are the 3 inferred relationships involving `ContactsScreen()` (e.g. with `RelaySearchBar()` and `ContactsPermission()`) actually correct?**
  _`ContactsScreen()` has 3 INFERRED edges - model-reasoned connections that need verification._