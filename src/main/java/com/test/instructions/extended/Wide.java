package com.test.instructions.extended;

import com.test.dataarea.Frame;
import com.test.instructions.base.BytecodeReader;
import com.test.instructions.base.Instruction;
import com.test.instructions.loads.aloadx.ALoad;
import com.test.instructions.loads.dloadx.DLoad;
import com.test.instructions.loads.floadx.FLoad;
import com.test.instructions.loads.iloadx.ILoad;
import com.test.instructions.loads.lloadx.LLoad;
import com.test.instructions.math.inc.IInc;
import com.test.instructions.stores.astorex.AStore;
import com.test.instructions.stores.dstorex.DStore;
import com.test.instructions.stores.fstorex.FStore;
import com.test.instructions.stores.istorex.IStore;
import com.test.instructions.stores.lstorex.LStore;

// 加载类指令、存储类指令、ret 指令和 iinc 指令需要按索引访问局部变量表，
// 索引以 uint8 的形式存在字节码中。对于大部分方法来说，局部变量表大小都不会超过256，
// 所以可以用一字节来表示索引
// 但如果有方法的局部变量表超过这一限制，需要使用 wide 指令扩展命令

// 扩展局部变量表，wide 指令改变其他指令的行为
public class Wide implements Instruction {

    // 被改变的新指令
    Instruction modifiedInstruction;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        int opcode = reader.readU1();
        switch (opcode) {
            case 0x15:
                // 创建子指令实例
                ILoad iLoad = new ILoad();
                // 读取子指令的操作数
                iLoad.setIndex(reader.readU2());
                this.modifiedInstruction = iLoad;
            case 0x16:
                LLoad lLoad = new LLoad();
                lLoad.setIndex(reader.readU2());
                this.modifiedInstruction = lLoad;
            case 0x17:
                FLoad fLoad = new FLoad();
                fLoad.setIndex(reader.readU2());
                this.modifiedInstruction = fLoad;
            case 0x18:
                DLoad dLoad = new DLoad();
                dLoad.setIndex(reader.readU2());
                this.modifiedInstruction = dLoad;
            case 0x19:
                ALoad aLoad = new ALoad();
                aLoad.setIndex(reader.readU2());
                this.modifiedInstruction = aLoad;
            case 0x36:
                IStore iStore = new IStore();
                iStore.setIndex(reader.readU2());
                this.modifiedInstruction = iStore;
            case 0x37:
                LStore lStore = new LStore();
                lStore.setIndex(reader.readU2());
                this.modifiedInstruction = lStore;
            case 0x38:
                FStore fStore = new FStore();
                fStore.setIndex(reader.readU2());
                this.modifiedInstruction = fStore;
            case 0x39:
                DStore dStore = new DStore();
                dStore.setIndex(reader.readU2());
                this.modifiedInstruction = dStore;
            case 0x3a:
                AStore aStore = new AStore();
                aStore.setIndex(reader.readU2());
                this.modifiedInstruction = aStore;
            case 0x84:
                IInc iInc = new IInc();
                iInc.setIndex(reader.readU2());
                iInc.setConstant(reader.readU2());
                this.modifiedInstruction = iInc;
            case 0xa9:
                throw new RuntimeException("unsupported opcode: 0xa9");
        }
    }

    @Override
    public void Execute(Frame frame) {
        this.modifiedInstruction.Execute(frame);
    }
}
