# Strartup Game Test Drive

## In the prep code of StartupBust class, we got 4 methods:
- 1. setUpGame()
- 2. startPlaying()
- 3. checkUserGuess()
- 4. finishGame()

The most important method is checkUserGuess(), which is the core of the game and is needed to be tested first.
While others:
1. setUpGame() -- Create Startup objects and name them by calling the ready-bake methods on the helper object.
2. startPlaying() -- Get user's input by calling the getUserInput() method on the helper object an throw it into checkUserGuess().
4. finishGame() -- Print out the result if the user performed well.

## To check the method checkUserGuess(), we would create a test class named StartupBustTestDrive(). Here's its prep code:
IMPORT the ArrayList class
DECLARE a new ArrayList object to hold Startups named startups
DECLARE an int variable named numOfGuesses and set it to 0
DECLARE an array of strings named guesses and set it with some strings
REPEAT 3 times:
  DECLARE a new Startup object
  SET the startup's name to a string
  ADD the startup to the startups ArrayList
  SET the startup's location
END REPEAT
REPEAT ervery String in the guesses array:
  SET the guess to the current String in the array
  REPEAT while anay Startups exist in the startups ArrayList:
    EVALUATE the current String by calling the checkUserGuess() method
  END REPEAT
END REPEAT

## And the below is the advice from the AI:
针对 `StartupBust` 的测试驱动开发（TDD），你的分析抓住了核心逻辑，但在具体的 Prep Code 设计上，我们可以让它更符合 TDD 的“小步快跑”原则。

### 📌 你的分析与设计点评

#### 优点
1.  **核心识别准确：** 你正确识别出 `checkUserGuess()` 是最需要测试的核心方法。
2.  **流程清晰：** 你的 Prep Code 逻辑上模拟了“创建对象 -> 模拟输入 -> 循环判断 -> 结束”的完整游戏闭环。

#### 需要改进的地方
1.  **测试粒度过大：** 你的 Prep Code 试图在一个测试中完成“初始化 -> 循环输入 -> 判定胜负”的所有事情。在 TDD 中，我们通常希望**一次只测试一种情况**（例如：测试“猜对”的情况，再测试“猜错”的情况）。
2.  **依赖用户输入：** 你的设计中提到了 `startPlaying()` 获取用户输入。在自动化测试中，我们不能等待真实用户输入，也不能依赖控制台交互。我们需要**硬编码（Hard-code）** 一组测试数据来模拟用户的输入。
3.  **状态管理：** `checkUserGuess()` 的返回值依赖于游戏状态（还有多少船没沉）。你的 Prep Code 需要明确地构造出“即将胜利”或“刚刚失败”的状态，而不仅仅是随机猜测。

---

### 🚀 更好的解答：针对 StartupBust 的 TDD 设计

我们将 `StartupBust` 的测试分解为几个具体的场景。由于 `SimpleStartup` 已经被测试过了，我们在测试 `StartupBust` 时，重点关注它**管理游戏流程**和**统计猜测次数**的能力。

#### 改进后的 Prep Code 设计思路

我们不需要模拟“用户玩游戏”，而是模拟“程序运行”。

**测试目标：** 验证 `checkUserGuess()` 能否正确更新游戏状态，以及 `finishGame()` 能否根据猜测次数给出正确评价。

**Prep Code 逻辑（伪代码）：**

```text
// 1. 初始化阶段 (Setup)
IMPORT ArrayList
CREATE a new StartupBust object named 'game'
CREATE a helper object (GameHelper) // 用于生成位置，或者直接手动设置

// 2. 构造测试数据 (Mocking Data)
// 我们手动创建 3 个 Startup 对象，并设置固定的位置，以便我们预知结果
// 假设每个 Startup 占据 3 个格子
CREATE Startup 'dotCom1', set cells to {0, 1, 2}
CREATE Startup 'dotCom2', set cells to {3, 4, 5}
CREATE Startup 'dotCom3', set cells to {6, 7, 8}

ADD these startups to the game's list (或者调用 game.setUpGame() 如果是公开方法)

// 3. 模拟游戏过程 (Execution)
// 我们不使用 startPlaying()，而是直接向 checkUserGuess() 发送预设的字符串
// 场景 A: 模拟一个完美的游戏 (18次猜测以内)
// 我们需要构造一系列输入，确保能击沉所有船，且次数 < 18

DECLARE an array of strings 'inputs' containing valid hits for all ships
// 例如: "0", "1", "2" (击沉船1), "3", "4", "5" (击沉船2), "6", "7", "8" (击沉船3)
// 加上一些 miss 比如 "9", "10"

REPEAT for each input in 'inputs'
    CALL game.checkUserGuess(input)
    // 在 TDD 中，这里应该断言 (Assert) 返回值是否正确
    // 例如：输入 "0" 应返回 "Hit"，输入 "2" (最后一个) 应返回 "Kill"
END REPEAT

// 4. 验证结果 (Verification)
// 此时游戏应该已经结束
CALL game.finishGame()
// 检查输出是否为 "Your were pretty fast..." (因为只用了约10次猜测)

// 5. 测试另一种情况：糟糕的游戏 (>18次猜测)
// 重置游戏或创建新实例
// 构造一个包含 20 次 "miss" 和最后击沉所有船的输入序列
// 验证 finishGame() 是否输出 "Too many guesses..."
```

### 💡 关键改进点解析

1.  **绕过 `startPlaying()`：**
    在测试代码中，我们直接调用 `checkUserGuess("0")`，而不是通过 `startPlaying()` 等待输入。这使得测试可以自动运行，无需人工干预。

2.  **控制变量：**
    你的原始设计中，`set the startup's location` 可能是随机的（如果依赖 Helper）。在测试中，我们必须**显式设置位置**（如 `{0,1,2}`），这样我们才能确切知道输入 `"1"` 会导致 "Hit"。

3.  **关注返回值：**
    `checkUserGuess()` 应该返回一个字符串（"Hit", "Miss", "Kill"）。你的 Prep Code 中只是 `EVALUATE`，更好的做法是**捕获这个返回值**并检查它是否符合预期。

### 📌 总结

针对 `StartupBust` 的 TDD，你的设计是一个很好的“系统测试”雏形，但作为“单元测试”，我们需要更精细地控制输入和状态。

**更好的解答核心在于：** 不要测试“玩游戏的过程”，而是测试“游戏逻辑对特定输入的反应”。通过预设固定的船位置和固定的猜测序列，我们可以精确验证 `checkUserGuess` 和 `finishGame` 的逻辑是否正确。